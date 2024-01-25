/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ItemSnapshotList
import androidx.paging.cachedIn
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.domain.usecase.GetPlaceListUseCase

@HiltViewModel
class SearchViewModel @Inject constructor(
  private val getPlaceListUseCase: GetPlaceListUseCase,
) : ViewModel() {
  private val _currentLocation = MutableStateFlow(LatLng(-1.0, -1.0))

  fun setCurrentLocation(latitude: Double, longitude: Double) {
    _currentLocation.value = LatLng(latitude, longitude)
  }

  // TODO 맵 zoom level, 내 위치가 변하면 값이 갱신 되어야 함
  // 반경 1km 내에 위치한 장소에 마커가 찍히도록
  @OptIn(ExperimentalCoroutinesApi::class)
  val placeList = _currentLocation
    .filter { location ->
      location.latitude != -1.0 && location.longitude != -1.0
    }.flatMapLatest { location ->
      getPlaceListUseCase(
        distance = 1000.0,
        latitude = location.latitude,
        longitude = location.longitude,
      )
    }.cachedIn(viewModelScope)

  private val _placeSnapshoList = MutableStateFlow(emptyList<PlaceEntity>())
  val placeSnapshotList: StateFlow<List<PlaceEntity>> = _placeSnapshoList.asStateFlow()

  fun updatePlaceSnapshotList(snapshot: ItemSnapshotList<PlaceEntity>) {
    val snapshotList = mutableListOf<PlaceEntity>()
    snapshot.toList().forEach { place ->
      if (place != null) {
        snapshotList.add(place)
      }
    }
    _placeSnapshoList.value = snapshotList
  }
}
