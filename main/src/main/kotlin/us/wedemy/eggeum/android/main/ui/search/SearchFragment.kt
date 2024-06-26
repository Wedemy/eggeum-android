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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.gms.location.LocationServices
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
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.model.CafeDetailModel
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.FragmentSearchBinding
import us.wedemy.eggeum.android.main.mapper.toUiModel
import us.wedemy.eggeum.android.main.ui.adapter.SearchCafeAdapter
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel
import us.wedemy.eggeum.android.main.viewmodel.SearchViewModel

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), OnMapReadyCallback, Overlay.OnClickListener {
  override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

  private val cafeDetailViewModel by activityViewModels<CafeDetailViewModel>()
  private val searchViewModel by viewModels<SearchViewModel>()

  private val searchCafeAdapter by lazy { SearchCafeAdapter(null) }

  private var naverMap: NaverMap? = null

  // TODO 마커 리스트를 뷰모델에서 관리
  private val markers = mutableListOf<Marker>()
  private val fusedLocationClient by lazy {
    LocationServices.getFusedLocationProviderClient(requireContext())
  }
  private val locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
  private val permissions =
    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

  private val requestMultiplePermissionsLauncher =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
      val allPermissionsGranted = permissions.entries.all { it.value }
      searchViewModel.setPermissionsGranted(allPermissionsGranted)
    }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.mvSearch.apply {
      onCreate(savedInstanceState)
      getMapAsync(this@SearchFragment)
    }
    initListener()
    initObserver()
  }

  override fun onMapReady(naverMap: NaverMap) {
    this.naverMap = naverMap.apply {
      cameraPosition = CameraPosition(
        LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude),
        ZOOM_LEVEL,
      )
      uiSettings.isScaleBarEnabled = false
      uiSettings.isZoomControlEnabled = false
      locationSource = this@SearchFragment.locationSource
      addOnCameraChangeListener { _, _ ->
        searchViewModel.setLastCameraLocation(cameraPosition.target.latitude, cameraPosition.target.longitude)
      }
    }
    moveToCameraToUserLocation()
  }

  private fun checkPermission() {
    val allPermissionsGranted = permissions.all {
      ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    if (!allPermissionsGranted) {
      if (shouldShowPermissionRationale()) {
        showLocationPermissionRationaleDialog()
      } else {
        requestPermissions()
      }
    } else {
      searchViewModel.setPermissionsGranted(true)
      naverMap?.locationTrackingMode = LocationTrackingMode.Follow
      moveToCameraToUserLocation()
    }
  }

  private fun initListener() {
    with(binding) {
      fabSearchTracking.setOnClickListener {
        naverMap?.locationTrackingMode = LocationTrackingMode.Follow
      }
      tietSearchCafe.setOnClickListener {
        cvSearchCafe.performClick()
      }
      cvSearchCafe.setOnClickListener {
        searchViewModel.setCurrentLocation(
          searchViewModel.lastCameraLocation.value.latitude,
          searchViewModel.lastCameraLocation.value.longitude,
        )
        val action =
          SearchFragmentDirections.actionFragmentSearchToFragmentSearchCafeFragment(searchViewModel.currentLocation.value)
        findNavController().safeNavigate(action)
      }
      cvCurrentMapSearchCafe.setOnClickListener {
        searchViewModel.setInitialCameraLocation(
          searchViewModel.lastCameraLocation.value.latitude,
          searchViewModel.lastCameraLocation.value.longitude,
        )
        searchViewModel.setCurrentLocation(
          searchViewModel.lastCameraLocation.value.latitude,
          searchViewModel.lastCameraLocation.value.longitude,
        )
        cvCurrentMapSearchCafe.visibility = View.GONE
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        searchViewModel.permissionsGranted.collect { granted ->
          if (granted) {
            naverMap?.locationTrackingMode = LocationTrackingMode.Follow
            moveToCameraToUserLocation()
          } else {
            naverMap?.locationTrackingMode = LocationTrackingMode.None
          }
        }
      }

      launch {
        searchViewModel.placeList.collectLatest { pagingData ->
          searchCafeAdapter.submitData(pagingData)
        }
      }

      launch {
        searchCafeAdapter.loadStateFlow
          .distinctUntilChangedBy { it.refresh }
          .collect { loadStates ->
            if (loadStates.source.refresh is LoadState.NotLoading) {
              searchViewModel.updatePlaceSnapshotList(searchCafeAdapter.snapshot())
            }
          }
      }

      launch {
        searchViewModel.placeSnapshotList.collect {
          addMarkersToMap(it)
        }
      }

      launch {
        searchViewModel.lastCameraLocation.collect { lastCameraLocation ->
          if (lastCameraLocation != searchViewModel.initialCameraLocation.value && searchViewModel.initialCameraLocation.value.latitude != -1.0) {
            binding.cvCurrentMapSearchCafe.visibility = View.VISIBLE
          } else {
            binding.cvCurrentMapSearchCafe.visibility = View.GONE
          }
        }
      }
    }
  }

  private fun addMarkersToMap(markers: List<PlaceEntity>) {
    clearMarkers()
    markers.forEach {
      createAndAddMarker(it)
    }
  }

  private fun clearMarkers() {
    markers.forEach { marker ->
      marker.map = null
    }
    markers.clear()
  }

  private fun createAndAddMarker(data: PlaceEntity) {
    val marker = Marker().apply {
      position = LatLng(data.latitude ?: return, data.longitude ?: return)
      icon = OverlayImage.fromResource(us.wedemy.eggeum.android.design.R.drawable.ic_map_marker_24)
      map = naverMap
      tag = data.id
      onClickListener = this@SearchFragment
    }
    markers.add(marker)
  }

  override fun onClick(overlay: Overlay): Boolean {
    val selectedPlaceModel = searchViewModel.placeSnapshotList.value.firstOrNull { it.id == overlay.tag }
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
      val action = SearchFragmentDirections.actionFragmentSearchToFragmentMap()
      findNavController().safeNavigate(action)
    }
    return true
  }

  @SuppressLint("MissingPermission")
  private fun moveToCameraToUserLocation() {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
      val cameraUpdate = CameraUpdate.scrollTo(LatLng(location.latitude, location.longitude))
      naverMap?.moveCamera(cameraUpdate)
      searchViewModel.setCurrentLocation(location.latitude, location.longitude)
      if (searchViewModel.initialCameraLocation.value.latitude == -1.0) {
        searchViewModel.setInitialCameraLocation(location.latitude, location.longitude)
      }
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
    AlertDialog.Builder(requireContext()).apply {
      setMessage(getString(R.string.location_permission_educational_message))
      setPositiveButton(getString(R.string.check)) { _, _ -> requestPermissions() }
      setNegativeButton(getString(R.string.cancel), null)
      show().also {
        it.getButton(DialogInterface.BUTTON_POSITIVE)
          .setTextColor(ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.teal_500))
        it.getButton(DialogInterface.BUTTON_NEGATIVE)
          .setTextColor(ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.gray_400))
      }
    }
  }

  override fun onStart() {
    super.onStart()
    binding.mvSearch.onStart()
  }

  override fun onResume() {
    super.onResume()
    binding.mvSearch.onResume()
    checkPermission()
  }

  override fun onPause() {
    binding.mvSearch.onPause()
    super.onPause()
  }

  // TODO: 지도 -> 정보수정제안 시, 이 부분에서 NPE 발생하여 주석 처리
//  override fun onSaveInstanceState(outState: Bundle) {
//    super.onSaveInstanceState(outState)
//    binding.mvSearch.onSaveInstanceState(outState)
//  }

  override fun onStop() {
    binding.mvSearch.onStop()
    super.onStop()
  }

  override fun onDestroyView() {
    clearMarkers()
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
