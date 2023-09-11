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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import us.wedemy.eggeum.android.common.util.getMutableStateFlow

@HiltViewModel
class InquiryViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
  private val _inquiryTitle = savedStateHandle.getMutableStateFlow(KEY_INQUIRY_TITLE, "")
  val inquiryTitle = _inquiryTitle.asStateFlow()

  private val _inquiryContent = savedStateHandle.getMutableStateFlow(KEY_INQUIRY_CONTENT, "")
  val inquiryContent = _inquiryContent.asStateFlow()

  fun setInquiryTitle(inquiryTitle: String) {
    _inquiryTitle.value = inquiryTitle
  }

  fun setInquiryContent(inquiryContent: String) {
    _inquiryContent.value = inquiryContent
  }

  val enableSendInquiry =
    combine(
      inquiryTitle,
      inquiryContent,
    ) { title, content ->
      title.isNotEmpty() && content.isNotEmpty()
    }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false,
      )

  private companion object {
    private const val KEY_INQUIRY_TITLE = "inquiry_title"
    private const val KEY_INQUIRY_CONTENT = "inquiry_content"
  }
}