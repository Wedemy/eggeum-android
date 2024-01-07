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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.domain.usecase.GetPlaceListUseCase
import us.wedemy.eggeum.android.common.model.CafeDetailModel

@HiltViewModel
class CafeDetailViewModel @Inject constructor(
  getPlaceListUseCase: GetPlaceListUseCase,
) : ViewModel() {
  private val _cafeDetailInfo = MutableStateFlow(CafeDetailModel())
  val cafeDetailInfo: StateFlow<CafeDetailModel> = _cafeDetailInfo.asStateFlow()

  // TODO 맵 zoom level, 내 위치가 변하면 값이 갱신 되어야 함
  val placeList = getPlaceListUseCase(_cafeDetailInfo.value.name).cachedIn(viewModelScope)

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

  fun setCafeDetailInfo(cafeDetailInfo: CafeDetailModel) {
    _cafeDetailInfo.value = cafeDetailInfo
  }
}
