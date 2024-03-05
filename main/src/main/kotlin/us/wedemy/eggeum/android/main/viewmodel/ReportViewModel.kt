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
import us.wedemy.eggeum.android.common.util.ErrorHandlerActions
import us.wedemy.eggeum.android.common.util.UiText
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.common.util.handleException
import us.wedemy.eggeum.android.domain.model.report.CreateReportEntity
import us.wedemy.eggeum.android.domain.usecase.CreateReportUseCase
import us.wedemy.eggeum.android.domain.usecase.LogoutUseCase
import us.wedemy.eggeum.android.main.R

@HiltViewModel
class ReportViewModel @Inject constructor(
  private val createReportUseCase: CreateReportUseCase,
  private val logoutUseCase: LogoutUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel(), ErrorHandlerActions {
  private val _reportTitle = savedStateHandle.getMutableStateFlow(KEY_REPORT_TITLE, "")
  val reportTitle = _reportTitle.asStateFlow()

  private val _reportContent = savedStateHandle.getMutableStateFlow(KEY_REPORT_CONTENT, "")
  val reportContent = _reportContent.asStateFlow()

  private val _navigateToReportCompleteEvent = MutableSharedFlow<Unit>(replay = 1)
  val navigateToReportCompleteEvent: SharedFlow<Unit> = _navigateToReportCompleteEvent.asSharedFlow()

  private val _showToastEvent = MutableSharedFlow<UiText>()
  val showToastEvent: SharedFlow<UiText> = _showToastEvent.asSharedFlow()

  private val _navigateToLoginEvent = MutableSharedFlow<Unit>(replay = 1)
  val navigateToLoginEvent: SharedFlow<Unit> = _navigateToLoginEvent.asSharedFlow()

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
      createReportUseCase(
        CreateReportEntity(
          title = reportTitle.value,
          content = reportContent.value,
        ),
      ).onSuccess {
        _navigateToReportCompleteEvent.emit(Unit)
      }.onFailure { exception ->
        handleException(exception, this@ReportViewModel)
      }
    }
  }

  override fun showServerErrorToast() {
    viewModelScope.launch {
      _showToastEvent.emit(UiText.StringResource(R.string.server_error_message))
    }
  }

  override fun showNetworkErrorToast() {
    viewModelScope.launch {
      _showToastEvent.emit(UiText.StringResource(R.string.network_error_message))
    }
  }

  override fun handleNotFoundException() {
    //
  }

  override fun handleRefreshTokenExpired() {
    viewModelScope.launch {
      logoutUseCase()
      _navigateToLoginEvent.emit(Unit)
    }
  }

  private companion object {
    private const val KEY_REPORT_TITLE = "report_title"
    private const val KEY_REPORT_CONTENT = "report_content"
  }
}
