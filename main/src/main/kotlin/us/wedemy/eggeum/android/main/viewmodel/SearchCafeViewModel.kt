/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.domain.usecase.DeleteRecentSearchPlaceUseCase
import us.wedemy.eggeum.android.domain.usecase.GetSearchPlaceListUseCase
import us.wedemy.eggeum.android.domain.usecase.InsertRecentSearchPlaceUseCase

@HiltViewModel
class SearchCafeViewModel @Inject constructor(
  getSearchPlaceListUseCase: GetSearchPlaceListUseCase,
  private val insertRecentSearchPlaceUseCase: InsertRecentSearchPlaceUseCase,
  private val deleteRecentSearchPlaceUseCase: DeleteRecentSearchPlaceUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _searchQuery: SaveableMutableStateFlow<String> =
    savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME, "")
  val searchQuery = _searchQuery.asStateFlow()

  @OptIn(FlowPreview::class)
  val debouncedSearchQuery: Flow<String?> = searchQuery
    .debounce(SEARCH_TIME_DELAY)
    .distinctUntilChanged()

  @OptIn(ExperimentalCoroutinesApi::class)
  val searchPlaceList: Flow<PagingData<PlaceEntity>> =
    debouncedSearchQuery.flatMapLatest { query ->
      getSearchPlaceListUseCase(query = query)
    }.cachedIn(viewModelScope)

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun insertRecentSearchPlace(placeEntity: PlaceEntity) {
    viewModelScope.launch {
      insertRecentSearchPlaceUseCase(placeEntity)
    }
  }

  fun deleteRecentSearchPlace(placeEntity: PlaceEntity) {
    viewModelScope.launch {
      deleteRecentSearchPlaceUseCase(placeEntity)
    }
  }

  private companion object {
    private const val KEY_CAFE_NAME = "cafe_name"
    private const val SEARCH_TIME_DELAY = 300L
  }
}
