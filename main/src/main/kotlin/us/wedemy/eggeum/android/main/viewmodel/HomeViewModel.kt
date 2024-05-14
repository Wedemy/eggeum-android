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
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.domain.usecase.GetNoticeListUseCase
import us.wedemy.eggeum.android.domain.usecase.GetPlaceListUseCase
import us.wedemy.eggeum.android.main.mapper.toNoticeCardModel

@HiltViewModel
class HomeViewModel @Inject constructor(
  getPlaceListUseCase: GetPlaceListUseCase,
  getNoticeListUseCase: GetNoticeListUseCase,
) : ViewModel() {

  private val _cafesList = MutableStateFlow<List<List<PlaceEntity>>>(listOf(listOf(), listOf(), listOf()))
  val cafesList: StateFlow<List<List<PlaceEntity>>> = _cafesList.asStateFlow()

  private val _newCafes = MutableStateFlow(listOf<PlaceEntity>())
  val newCafes: StateFlow<List<PlaceEntity>> = _newCafes.asStateFlow()

  private val _newStudyCafes = MutableStateFlow(listOf<PlaceEntity>())
  val newStudyCafes: StateFlow<List<PlaceEntity>> = _newStudyCafes.asStateFlow()

  private val _newStudyRooms = MutableStateFlow(listOf<PlaceEntity>())
  val newStudyRooms: StateFlow<List<PlaceEntity>> = _newStudyRooms.asStateFlow()

  val cafeList = getPlaceListUseCase().cachedIn(viewModelScope)

  val noticeList = getNoticeListUseCase()
    .map { pagingData ->
      pagingData.map { notice ->
        notice.toNoticeCardModel()
      }
    }
    .cachedIn(viewModelScope)

  fun getNewCafeList(snapshot: ItemSnapshotList<PlaceEntity>) {
    val newCafeList = mutableListOf<PlaceEntity>()
    val newStudyCafeList = mutableListOf<PlaceEntity>()
    val newStudyRoomList = mutableListOf<PlaceEntity>()

    snapshot.toList().forEach { place ->
      place?.let {
        when (it.type) {
          CAFE_TYPE -> newCafeList.add(it)
          STUDY_CAFE_TYPE -> newStudyCafeList.add(it)
          STUDY_ROOM_TYPE -> newStudyRoomList.add(it)
        }
      }
    }
    _newCafes.value = newCafeList.take(3)
    _newStudyCafes.value = newStudyCafeList.take(3)
    _newStudyRooms.value = newStudyRoomList.take(3)

    _cafesList.value = listOf(newCafes.value, newStudyCafes.value, newStudyRooms.value)
    Timber.tag("cafesList").d("${cafesList.value}")
  }

  private companion object {
    private const val CAFE_TYPE = "CAFE"
    private const val STUDY_CAFE_TYPE = "STUDY_CAFE"
    private const val STUDY_ROOM_TYPE = "STUDY_ROOM"
  }
}
