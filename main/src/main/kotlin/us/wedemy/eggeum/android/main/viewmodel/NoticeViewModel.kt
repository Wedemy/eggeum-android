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
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import us.wedemy.eggeum.android.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.domain.usecase.GetNoticeListUseCase
import us.wedemy.eggeum.android.main.mapper.toNoticModel
import us.wedemy.eggeum.android.main.model.NoticeModel

@HiltViewModel
class NoticeViewModel @Inject constructor(
  getNoticeListUseCase: GetNoticeListUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _searchQuery: SaveableMutableStateFlow<String> =
    savedStateHandle.getMutableStateFlow(KEY_SEARCH_KEYWORD, "")
  val searchQuery = _searchQuery.asStateFlow()

  @OptIn(FlowPreview::class)
  val debouncedSearchQuery: Flow<String?> = searchQuery
    .debounce(SEARCH_TIME_DELAY)
    .distinctUntilChanged()

  @OptIn(ExperimentalCoroutinesApi::class)
  val noticeList: Flow<PagingData<NoticeModel>> =
    debouncedSearchQuery.flatMapLatest { query ->
      getNoticeListUseCase(query).map { pagingData ->
        pagingData.map { notice ->
          notice.toNoticModel()
        }
      }
    }.cachedIn(viewModelScope)

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  private companion object {
    private const val KEY_SEARCH_KEYWORD = "search_keyword"
    private const val SEARCH_TIME_DELAY = 300L
  }
}
