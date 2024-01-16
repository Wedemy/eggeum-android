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
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.domain.usecase.GetNoticeListUseCase
import us.wedemy.eggeum.android.main.mapper.toNoticModel

@HiltViewModel
class NoticeViewModel @Inject constructor(
  getNoticeListUseCase: GetNoticeListUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _searchKeyword = savedStateHandle.getMutableStateFlow(KEY_SEARCH_KEYWORD, "")
  val searchKeyword: StateFlow<String> = _searchKeyword.asStateFlow()

  val noticeList = getNoticeListUseCase()
    .map { pagingData ->
      pagingData.map { notice ->
        notice.toNoticModel()
      }
    }
    .cachedIn(viewModelScope)

  fun setSearchKeyword(searchKeyword: String) {
    _searchKeyword.value = searchKeyword
  }

  private companion object {
    private const val KEY_SEARCH_KEYWORD = "search_keyword"
  }
}
