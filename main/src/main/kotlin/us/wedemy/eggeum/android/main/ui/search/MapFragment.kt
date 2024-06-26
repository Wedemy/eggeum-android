/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.util.fadeInView
import us.wedemy.eggeum.android.common.util.fadeOutView
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.FragmentMapBinding
import us.wedemy.eggeum.android.main.mapper.toUiModel
import us.wedemy.eggeum.android.common.model.CafeDetailModel
import us.wedemy.eggeum.android.main.ui.MainActivity
import us.wedemy.eggeum.android.main.ui.adapter.SearchCafeAdapter
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel
import us.wedemy.eggeum.android.main.viewmodel.MapViewModel

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(), OnMapReadyCallback, Overlay.OnClickListener {

  private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

  private val cafeDetailViewModel by activityViewModels<CafeDetailViewModel>()
  private val mapViewModel by viewModels<MapViewModel>()
  override fun getViewBinding(): FragmentMapBinding = FragmentMapBinding.inflate(layoutInflater)

  private val searchCafeAdapter by lazy { SearchCafeAdapter(null) }

  private var naverMap: NaverMap? = null
  private val markers = mutableListOf<Marker>()
  private lateinit var fusedLocationClient: FusedLocationProviderClient
  private val locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
  private val permissions =
    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
  private var permissionsGranted = false

  private val requestMultiplePermissionsLauncher =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { _ ->
      if (isPermissionsGranted()) {
        permissionsGranted = true
      } else {
        naverMap?.locationTrackingMode = LocationTrackingMode.None
      }
    }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    binding.mvSearch.apply {
      onCreate(savedInstanceState)
      getMapAsync(this@MapFragment)
    }
    checkPermission()
    initCafeDetailBottomSheet()
    initView()
    initListener()
    initObserver()
  }

  private fun checkPermission() {
    if (shouldShowPermissionRationale()) {
      showLocationPermissionRationaleDialog()
    } else {
      requestPermissions()
    }
  }

  private fun initCafeDetailBottomSheet() {
    bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.root)
    val screenHeight = getScreenHeight()
    bottomSheetBehavior.apply {
      peekHeight = (screenHeight * 0.6).toInt()
      // peekHeight 를 0.6 으로, state 를 collapsed 로 해야 0.6 만큼 차지함
      // Half expanded 로 설정할 경우 0.6 이 무시됨
      state = BottomSheetBehavior.STATE_COLLAPSED
    }
    bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
      override fun onSlide(bottomSheet: View, slideOffset: Float) {
        // 0 ~ 1
        if (slideOffset > 0.8f) {
          bottomSheetBehavior.apply {
            BottomSheetBehavior.STATE_EXPANDED
            skipCollapsed = true
          }
        }
      }

      override fun onStateChanged(bottomSheet: View, newState: Int) {
        when (newState) {
          BottomSheetBehavior.STATE_COLLAPSED -> {
            binding.bottomSheet.apply {
              fadeInView(ivCafeDetailHandle)
              fadeOutView(ivCafeDetailShrink)
              bottomSheet.background =
                ContextCompat.getDrawable(requireContext(), us.wedemy.eggeum.android.design.R.drawable.radius_21)
            }
          }
          BottomSheetBehavior.STATE_DRAGGING -> {}
          BottomSheetBehavior.STATE_EXPANDED -> {
            binding.bottomSheet.apply {
              fadeInView(ivCafeDetailShrink)
              fadeOutView(ivCafeDetailHandle)
              bottomSheet.background =
                ContextCompat.getDrawable(requireContext(), us.wedemy.eggeum.android.design.R.drawable.radius_0)
            }
          }
          BottomSheetBehavior.STATE_HIDDEN -> {}
          BottomSheetBehavior.STATE_SETTLING -> {}
          BottomSheetBehavior.STATE_HALF_EXPANDED -> {
            binding.bottomSheet.apply {
              fadeInView(ivCafeDetailHandle)
              fadeOutView(ivCafeDetailShrink)
              bottomSheet.background =
                ContextCompat.getDrawable(requireContext(), us.wedemy.eggeum.android.design.R.drawable.radius_21)
            }
          }
        }
      }
    })

    // 바텀 시트가 화면을 꽉 채웠을 때, 뒤로가기 버튼을 누르면, 화면을 나가지 않고, 바텀시트가 축소 되도록
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
          // 바텀시트가 확장된 상태일 때 백 버튼을 누르면, 초기 상태(60% 높이)로 변경
          bottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheetBehavior.skipCollapsed = true
          }
        } else {
          isEnabled = false
          requireActivity().onBackPressed()
        }
      }
    })
  }

  private fun getScreenHeight(): Int {
    val displayMetrics = DisplayMetrics()
    requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
  }

  private fun initView() {
    showFragment(TAG_CAFE_INFO_FRAGMENT)
    updateCafeInfo(cafeDetailViewModel.cafeDetailInfo.value)
  }

  private fun updateCafeInfo(cafeDetailInfo: CafeDetailModel) {
    binding.bottomSheet.apply {
      tvCafeDetailName.text = cafeDetailInfo.name
      tvCafeDetailAddress.text = cafeDetailInfo.address1
    }
  }

  @SuppressLint("CommitTransaction")
  private fun showFragment(fragmentTag: String) {
    val existingFragment = childFragmentManager.findFragmentByTag(fragmentTag)
    childFragmentManager.beginTransaction().apply {
      childFragmentManager.fragments.forEach { hide(it) }
      if (existingFragment == null) {
        val newFragment = when (fragmentTag) {
          TAG_CAFE_INFO_FRAGMENT -> CafeInfoFragment()
          TAG_CAFE_IMAGE_FRAGMENT -> CafeImageFragment()
          TAG_CAFE_MENU_FRAGMENT -> CafeMenuFragment()
          else -> error("Unknown fragment tag: $fragmentTag")
        }
        add(R.id.child_fragment_container, newFragment, fragmentTag)
      } else {
        show(existingFragment)
      }
      commit()
    }
  }

  private fun initListener() {
    with(binding) {
      bottomSheet.tlCafeDetail.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
          when (tab?.position) {
            0 -> showFragment(TAG_CAFE_INFO_FRAGMENT)
            1 -> showFragment(TAG_CAFE_IMAGE_FRAGMENT)
            2 -> showFragment(TAG_CAFE_MENU_FRAGMENT)
          }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
        override fun onTabReselected(tab: TabLayout.Tab?) = Unit
      })

      bottomSheet.ivCafeDetailOption.setOnClickListener {
        val popupMenu = PopupMenu(binding.root.context, it)
        popupMenu.menuInflater.inflate(R.menu.cafe_detail_menu, popupMenu.menu)

        popupMenu.setForceShowIcon(true)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
          if (it.itemId == R.id.proposal_info_edit) {
            mapViewModel.navigateToUpdateCafe()
            true
          } else false
        }
      }

      bottomSheet.ivCafeDetailShrink.setOnClickListener {
        bottomSheetBehavior.apply {
          state = BottomSheetBehavior.STATE_HALF_EXPANDED
          skipCollapsed = true
        }
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        cafeDetailViewModel.placeList.collectLatest { pagingData ->
          searchCafeAdapter.submitData(pagingData)
        }
      }

      launch {
        searchCafeAdapter.loadStateFlow
          .distinctUntilChangedBy { it.refresh }
          .collect { loadStates ->
            if (loadStates.source.refresh is LoadState.NotLoading) {
              cafeDetailViewModel.updatePlaceSnapshotList(searchCafeAdapter.snapshot())
              addMarkersToMap(searchCafeAdapter.snapshot())
            }
          }
      }

      launch {
        cafeDetailViewModel.cafeDetailInfo.collect { cafeDetailModel ->
          updateCafeInfo(cafeDetailModel)
          initNaverMap()
        }
      }

      launch {
        mapViewModel.navigateToUpdateCafeEvent.collect {
          (activity as MainActivity).navigateToUpdateCafe(cafeDetailViewModel.cafeDetailInfo.value)
        }
      }
    }
  }

  override fun onMapReady(naverMap: NaverMap) {
    this.naverMap = naverMap
    if (permissionsGranted) {
      naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }
    initNaverMap()
    addMarkersToMap(searchCafeAdapter.snapshot())
  }

  private fun initNaverMap() {
    naverMap?.apply {
      locationSource = this@MapFragment.locationSource
      uiSettings.isScaleBarEnabled = false
      uiSettings.isZoomControlEnabled = false
      val cafeDetailInfo = cafeDetailViewModel.cafeDetailInfo.value
      cameraPosition = CameraPosition(
        LatLng(cafeDetailInfo.latitude!!, cafeDetailInfo.longitude!!),
        ZOOM_LEVEL,
      )
      setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)
    }
  }

  private fun addMarkersToMap(snapshot: ItemSnapshotList<PlaceEntity>) {
    snapshot.toList().forEach { place ->
      if (place != null) {
        createAndAddMarker(place)
      }
    }
  }

  private fun createAndAddMarker(data: PlaceEntity) {
    val marker = Marker()
    if (data.latitude != null && data.longitude != null) {
      marker.position = LatLng(data.latitude!!, data.longitude!!)
      markers.add(marker)
      marker.map = naverMap
    }
    marker.tag = data.id
    val selectedCafeInfo = cafeDetailViewModel.cafeDetailInfo.value
    if (marker.position.latitude == selectedCafeInfo.latitude && marker.position.longitude == selectedCafeInfo.longitude) {
      marker.icon = OverlayImage.fromResource(us.wedemy.eggeum.android.design.R.drawable.ic_map_marker_40)
    } else {
      marker.icon = OverlayImage.fromResource(us.wedemy.eggeum.android.design.R.drawable.ic_map_marker_24)
    }
    marker.onClickListener = this@MapFragment
    markers.add(marker)
  }

  private fun isPermissionsGranted(): Boolean {
    return permissions.all {
      ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
  }

  private fun requestPermissions() {
    requestMultiplePermissionsLauncher.launch(permissions)
  }

  private fun shouldShowPermissionRationale(): Boolean {
    return (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ||
      shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION))
  }

  private fun showLocationPermissionRationaleDialog() {
    val dialog = AlertDialog.Builder(requireContext())
      .setMessage(getString(R.string.location_permission_educational_message))
      .setPositiveButton(getString(R.string.check)) { _, _ ->
        requestPermissions()
      }
      .setNegativeButton(getString(R.string.cancel), null)
      .show()
    dialog.apply {
      getButton(DialogInterface.BUTTON_POSITIVE)
        .setTextColor(ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.teal_500))
      getButton(DialogInterface.BUTTON_NEGATIVE)
        .setTextColor(ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.gray_400))
    }
  }

  override fun onClick(overlay: Overlay): Boolean {
    val selectedPlaceModel = cafeDetailViewModel.placeSnapshotList.value.firstOrNull { it.id == overlay.tag }
    for (marker in markers) {
      if (marker.tag == overlay.tag) {
        marker.icon = OverlayImage.fromResource(us.wedemy.eggeum.android.design.R.drawable.ic_map_marker_40)
      } else {
        marker.icon = OverlayImage.fromResource(us.wedemy.eggeum.android.design.R.drawable.ic_map_marker_24)
      }
    }
    if (selectedPlaceModel != null) {
      val cafeDetailInfo = CafeDetailModel(
        address1 = selectedPlaceModel.address1,
        address2 = selectedPlaceModel.address2,
        id = selectedPlaceModel.id,
        image = selectedPlaceModel.image?.toUiModel(),
        info = selectedPlaceModel.info?.toUiModel(),
        latitude = selectedPlaceModel.latitude,
        longitude = selectedPlaceModel.longitude,
        menu = selectedPlaceModel.menu?.toUiModel(),
        name = selectedPlaceModel.name,
      )
      if (selectedPlaceModel.latitude != null && selectedPlaceModel.longitude != null) {
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(selectedPlaceModel.latitude!!, selectedPlaceModel.longitude!!))
          .animate(CameraAnimation.Easing)
        naverMap?.moveCamera(cameraUpdate)
      }
      cafeDetailViewModel.setCafeDetailInfo(cafeDetailInfo)
    }
    return true
  }

  override fun onStart() {
    super.onStart()
    binding.mvSearch.onStart()
  }

  override fun onResume() {
    super.onResume()
    binding.mvSearch.onResume()
  }

  override fun onPause() {
    binding.mvSearch.onPause()
    super.onPause()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    binding.mvSearch.onSaveInstanceState(outState)
  }

  override fun onStop() {
    binding.mvSearch.onStop()
    super.onStop()
  }

  override fun onDestroyView() {
    markers.forEach { marker ->
      marker.map = null
    }
    markers.clear()
    binding.mvSearch.onDestroy()
    naverMap = null
    super.onDestroyView()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    binding.mvSearch.onLowMemory()
  }

  private companion object {
    private const val TAG_CAFE_INFO_FRAGMENT = "CafeInfoFragment"
    private const val TAG_CAFE_IMAGE_FRAGMENT = "CafeImageFragment"
    private const val TAG_CAFE_MENU_FRAGMENT = "CafeMenuFragment"
    private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private const val ZOOM_LEVEL = 15.0
  }
}
