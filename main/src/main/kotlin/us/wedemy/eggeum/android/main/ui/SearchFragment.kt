/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.FragmentSearchBinding
import us.wedemy.eggeum.android.common.ui.BaseFragment

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), OnMapReadyCallback {
  override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

  private lateinit var naverMap: NaverMap
  private lateinit var fusedLocationClient: FusedLocationProviderClient
  private val locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
  private val permissions =
    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
  private var permissionsGranted = false

  private val requestMultiplePermissionsLauncher =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
      if (isPermissionsGranted()) {
        permissionsGranted = true
      } else {
        naverMap.locationTrackingMode = LocationTrackingMode.None
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
  }

  private fun isPermissionsGranted(): Boolean {
    return permissions.all {
      ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
  }

  private fun checkPermission() {
    if (shouldShowPermissionRationale()) showLocationPermissionRationaleDialog()
    else requestPermissions()
  }

  private fun initListener() {
    binding.fabSearchTracking.setOnClickListener {
      naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }

    binding.tietSearchCafe.setOnClickListener {
      binding.cvSearchCafe.performClick()
    }

    binding.cvSearchCafe.setOnClickListener {
      val action = SearchFragmentDirections.actionFragmentSearchToSearchCafeFragment()
      findNavController().safeNavigate(action)
    }
  }

  private fun initObserver() {
    // TODO 현재 보여지는 지도 내에 등록된 카페 목록 가져오기
  }

  private fun initNaverMap() {
    naverMap.apply {
      locationSource = this@SearchFragment.locationSource
      uiSettings.isZoomControlEnabled = false
      cameraPosition = CameraPosition(
        LatLng(naverMap.cameraPosition.target.latitude, naverMap.cameraPosition.target.longitude),
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
      naverMap.moveCamera(cameraUpdate)
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
    binding.mvSearch.onDestroy()
    super.onDestroyView()
  }

  override fun onLowMemory() {
    binding.mvSearch.onLowMemory()
    super.onLowMemory()
  }

  private companion object {
    private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private const val ZOOM_LEVEL = 15.0
  }
}
