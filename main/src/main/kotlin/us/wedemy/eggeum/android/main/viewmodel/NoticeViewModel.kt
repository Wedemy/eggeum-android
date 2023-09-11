/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import us.wedemy.eggeum.android.common.util.getMutableStateFlow

@HiltViewModel
class NoticeViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
  private val _searchKeyword = savedStateHandle.getMutableStateFlow(KEY_SEARCH_KEYWORD, "")
  val searchKeyword = _searchKeyword.asStateFlow()

  init {
    getNoticeList()
  }

  private fun getNoticeList() {
    // TODO 공지사항 리스트 조회 구현
  }

  fun setSearchKeyword(searchKeyword: String) {
    _searchKeyword.value = searchKeyword
  }

  private companion object {
    private const val KEY_SEARCH_KEYWORD = "search_keyword"
  }
}