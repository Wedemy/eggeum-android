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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.domain.usecase.GetPlaceListUseCase
import us.wedemy.eggeum.android.common.model.CafeDetailModel

@HiltViewModel
class CafeDetailViewModel @Inject constructor(
  getPlaceListUseCase: GetPlaceListUseCase,
) : ViewModel() {
  private val _cafeDetailInfo = MutableStateFlow(CafeDetailModel())
  val cafeDetailInfo: StateFlow<CafeDetailModel> = _cafeDetailInfo.asStateFlow()

  @OptIn(ExperimentalCoroutinesApi::class)
  val placeList = _cafeDetailInfo
    .map { it.name }
    .filter {
      it.isNotEmpty()
    }.flatMapLatest { name ->
      getPlaceListUseCase(query = name)
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

  fun setCafeDetailInfo(cafeDetailInfo: CafeDetailModel) {
    _cafeDetailInfo.value = cafeDetailInfo
  }
}
