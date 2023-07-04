/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.main.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.common.ui.BaseFragment
import us.wedemy.eggeum.design.R
import us.wedemy.eggeum.main.databinding.FragmentSearchBinding
import us.wedemy.eggeum.main.viewmodel.SearchViewModel

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), OnMapReadyCallback {
  override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

  private lateinit var locationSource: FusedLocationSource
  private lateinit var naverMap: NaverMap

  private val requestPermissionLauncher =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
      if (isGranted) {
        // Permission is granted. Continue the action or workflow in your app.
      } else {
        // Permission is not granted
        naverMap.locationTrackingMode = LocationTrackingMode.None
      }
    }

  private val viewModel by viewModels<SearchViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

    when {
      ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
      ) == PackageManager.PERMISSION_GRANTED -> {
        // You can use the API that requires the permission.
        // proceed with your logic
      }
      shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
        // Show an explanation to the user as to why your app needs the permission.
        // proceed with requesting for permission
      }
      else -> {
        // request permission
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
      }
    }

    with(binding) {
      mvSearch.onCreate(savedInstanceState)
      mvSearch.getMapAsync(this@SearchFragment)
    }

    initListener()
    initObserver()
  }

  private fun initListener() {}

  private fun initObserver() {}
  override fun onMapReady(naverMap: NaverMap) {
    this.naverMap = naverMap
    naverMap.locationSource = locationSource

    val options = NaverMapOptions()
      .camera(CameraPosition(LatLng(37.566, 126.978), 10.0))
      .mapType(NaverMap.MapType.Basic)
      .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)

    MapFragment.newInstance(options)

    val marker = Marker()
    marker.apply {
      // position = LatLng(naverMap.cameraPosition.target.latitude, naverMap.cameraPosition.target.longitude)
      position = LatLng(37.566, 126.978)
      icon = OverlayImage.fromResource(R.drawable.ic_pin_colored_24)
      map = naverMap
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
    super.onPause()
    binding.mvSearch.onPause()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    binding.mvSearch.onSaveInstanceState(outState)
  }

  override fun onStop() {
    super.onStop()
    binding.mvSearch.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    binding.mvSearch.onLowMemory()
  }

  private companion object {
    private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
  }
}