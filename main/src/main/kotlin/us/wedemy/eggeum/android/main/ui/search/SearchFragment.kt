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
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ItemSnapshotList
import androidx.paging.LoadState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.FragmentSearchBinding
import us.wedemy.eggeum.android.main.mapper.toUiModel
import us.wedemy.eggeum.android.main.mapper.toUilModel
import us.wedemy.eggeum.android.main.model.CafeDetailModel
import us.wedemy.eggeum.android.main.model.ImageModel
import us.wedemy.eggeum.android.main.model.InfoModel
import us.wedemy.eggeum.android.main.model.MenuModel
import us.wedemy.eggeum.android.main.model.ProductModel
import us.wedemy.eggeum.android.main.ui.adapter.CafePagingAdapter
import us.wedemy.eggeum.android.main.viewmodel.SearchViewModel

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), OnMapReadyCallback {
  override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

  private val viewModel by viewModels<SearchViewModel>()

  private val cafePagingAdapter by lazy { CafePagingAdapter() }

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
    with(binding) {
      mvSearch.onCreate(savedInstanceState)
      mvSearch.getMapAsync(this@SearchFragment)
    }
    checkPermission()
    initListener()
    initObserver()
  }

  override fun onMapReady(naverMap: NaverMap) {
    this.naverMap = naverMap
    if (permissionsGranted) {
      naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }
    initNaverMap()
    addMarkersToMap(cafePagingAdapter.snapshot())
  }

  private fun isPermissionsGranted(): Boolean {
    return permissions.all {
      ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
  }

  private fun checkPermission() {
    if (shouldShowPermissionRationale()) {
      showLocationPermissionRationaleDialog()
    } else {
      requestPermissions()
    }
  }

  private fun initListener() {
    // TODO 마커 클릭 화면 전환 클릭 이벤트 리스터 구현
    binding.fabSearchTracking.setOnClickListener {
      naverMap?.locationTrackingMode = LocationTrackingMode.Follow
    }

    binding.tietSearchCafe.setOnClickListener {
      binding.cvSearchCafe.performClick()
    }

    binding.cvSearchCafe.setOnClickListener {
      val action = SearchFragmentDirections.actionFragmentSearchToFragmentCafeDetail(
        CafeDetailModel(
          address1 = "서울특별시 강남구 강남대로 396",
          address2 = "",
          id = 1,
          image = ImageModel(
            listOf()
          ),
          info = InfoModel(
            areaSize = "30",
            meetingRoomCount = 3,
            multiSeatCount = 24,
            singleSeatCount = 1,
            businessHours = listOf("매일 09:00 - 21:00"),
            existsSmokingArea = false,
            existsWifi = true,
            mobileCharging = "카운터에서 가능",
            parking = "가능 / 기본 1시간 / 시간당 3,000원",
            phone = "02-123-4567",
            restRoom = "내부 / 남녀분리 / 장애인 화장실 있음",
            websiteUri = "",
            instagramUri = "",
            blogUri = "",
          ),
          menu = MenuModel(
            listOf(ProductModel("아메리카노", 3000), ProductModel("카페라테", 5000),)
          ),
          name = "스타벅스 강남역신분당역사점"
        )
      )
      findNavController().safeNavigate(action)
    }

    // https://navermaps.github.io/android-map-sdk/guide-ko/4-1.html
    for (i in 0..< markers.size) {
      // TODO 클릭 리스너가 동작하지 않는 문제 해결
      // TODO 클릭한 마커 이미지 변경
      markers[i].setOnClickListener {
        Timber.d("marker $i clicked")
        val placeModel = viewModel.placeSnapshotList.value[i]
        val cafeDetailModel = CafeDetailModel(
          address1 = placeModel.address1,
          address2 = placeModel.address2,
          id = placeModel.id,
          image = placeModel.image.toUiModel(),
          info = placeModel.info.toUilModel(),
          menu = placeModel.menu.toUiModel(),
          name = placeModel.name,
        )
        Timber.d("$cafeDetailModel")
        val action = SearchFragmentDirections.actionFragmentSearchToFragmentCafeDetail(cafeDetailModel)
        findNavController().safeNavigate(action)
        false
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.placeList.collectLatest { pagingData ->
          cafePagingAdapter.submitData(pagingData)
        }
      }

      launch {
        cafePagingAdapter.loadStateFlow
          .distinctUntilChangedBy { it.refresh }
          .collect { loadStates ->
            if (loadStates.source.refresh is LoadState.NotLoading) {
              viewModel.updatePlaceSnapshotList(cafePagingAdapter.snapshot())
              addMarkersToMap(cafePagingAdapter.snapshot())
            }
          }
      }
    }
  }

  private fun addMarkersToMap(snapshot: ItemSnapshotList<PlaceEntity>) {
    snapshot.toList().forEach { place ->
      if (place != null) {
        createAndAddMarker(place)
      }
    }
    markers.forEach { marker ->
      marker.icon = OverlayImage.fromResource(us.wedemy.eggeum.android.design.R.drawable.ic_map_marker_24)
      marker.map = naverMap
    }
  }

  private fun createAndAddMarker(data: PlaceEntity) {
    val marker = Marker()
    marker.position = LatLng(data.latitude, data.longitude)
    markers.add(marker)
    marker.map = naverMap
  }

  private fun initNaverMap() {
    naverMap?.apply {
      locationSource = this@SearchFragment.locationSource
      uiSettings.isZoomControlEnabled = false
      cameraPosition = CameraPosition(
        LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude),
        ZOOM_LEVEL,
      )
      setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)
    }
    moveToCameraToUserLocation()
  }

  @SuppressLint("MissingPermission")
  private fun moveToCameraToUserLocation() {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
      val cameraUpdate = CameraUpdate.scrollTo(LatLng(location.latitude, location.longitude))
      naverMap?.moveCamera(cameraUpdate)
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
    private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private const val ZOOM_LEVEL = 15.0
  }
}
