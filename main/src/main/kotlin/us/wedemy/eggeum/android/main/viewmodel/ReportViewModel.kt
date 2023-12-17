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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.domain.model.report.CreateReportEntity
import us.wedemy.eggeum.android.domain.usecase.CreateReportUseCase

@HiltViewModel
class ReportViewModel @Inject constructor(
  private val createReportUseCase: CreateReportUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _reportTitle = savedStateHandle.getMutableStateFlow(KEY_REPORT_TITLE, "")
  val reportTitle = _reportTitle.asStateFlow()

  private val _reportContent = savedStateHandle.getMutableStateFlow(KEY_REPORT_CONTENT, "")
  val reportContent = _reportContent.asStateFlow()

  private val _navigateToReportCompleteEvent = MutableSharedFlow<Unit>(replay = 1)
  val navigateToReportCompleteEvent: SharedFlow<Unit> = _navigateToReportCompleteEvent.asSharedFlow()

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()

  fun setReportTitle(reportTitle: String) {
    _reportTitle.value = reportTitle
  }

  fun setReportContent(reportContent: String) {
    _reportContent.value = reportContent
  }

  val enableSendReport =
    combine(
      reportTitle,
      reportContent,
    ) { title, content ->
      title.isNotEmpty() && content.isNotEmpty()
    }.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = false,
    )

  fun createReport() {
    viewModelScope.launch {
      val result = createReportUseCase(
        CreateReportEntity(
          title = reportTitle.value,
          content = reportContent.value,
        )
      )
      when {
        result.isSuccess -> {
          _navigateToReportCompleteEvent.emit(Unit)
        }
        result.isFailure -> {
          val exception = result.exceptionOrNull()
          Timber.d(exception)
          _showToastEvent.emit(exception?.message ?: "Unknown Error Occured")
        }
      }
    }
  }

  private companion object {
    private const val KEY_REPORT_TITLE = "report_title"
    private const val KEY_REPORT_CONTENT = "report_content"
  }
}
