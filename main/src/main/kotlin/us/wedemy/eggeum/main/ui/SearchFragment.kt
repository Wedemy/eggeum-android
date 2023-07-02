/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.main.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.common.ui.BaseFragment
import us.wedemy.eggeum.main.databinding.FragmentSearchBinding
import us.wedemy.eggeum.main.viewmodel.SearchViewModel

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), OnMapReadyCallback {
  override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

  private val viewModel by viewModels<SearchViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    with(binding) {
      mapViewSearch.onCreate(savedInstanceState)
      mapViewSearch.getMapAsync(this@SearchFragment)
    }

    initListener()
    initObserver()
  }

  private fun initListener() {}

  private fun initObserver() {}
  override fun onMapReady(naverMap: NaverMap) {
    val options = NaverMapOptions()
      .camera(CameraPosition(LatLng(37.566, 126.978), 10.0))
      .mapType(NaverMap.MapType.Basic)
      .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)

    MapFragment.newInstance(options)

    val marker = Marker()
    marker.position = LatLng(37.566, 126.978)
    marker.map = naverMap
  }

  override fun onStart() {
    super.onStart()
    binding.mapViewSearch.onStart()
  }

  override fun onResume() {
    super.onResume()
    binding.mapViewSearch.onResume()
  }

  override fun onPause() {
    super.onPause()
    binding.mapViewSearch.onPause()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    binding.mapViewSearch.onSaveInstanceState(outState)
  }

  override fun onStop() {
    super.onStop()
    binding.mapViewSearch.onStop()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    binding.mapViewSearch.onLowMemory()
  }
}