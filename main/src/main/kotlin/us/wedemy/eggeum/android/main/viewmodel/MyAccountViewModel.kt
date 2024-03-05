/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.util.ErrorHandlerActions
import us.wedemy.eggeum.android.common.util.UiText
import us.wedemy.eggeum.android.common.util.handleException
import us.wedemy.eggeum.android.domain.usecase.GetUserInfoUseCase
import us.wedemy.eggeum.android.domain.usecase.LogoutUseCase
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.mapper.toUiModel
import us.wedemy.eggeum.android.main.model.ProfileImageModel

data class MyAccountUiState(
  val nickname: String = "",
  val email: String = "",
  val profileImageModel: ProfileImageModel? = null,
  val isLoading: Boolean = false,
  val error: Throwable? = null,
)

@HiltViewModel
class MyAccountViewModel @Inject constructor(
  private val getUserInfoUseCase: GetUserInfoUseCase,
  private val logoutUseCase: LogoutUseCase,
) : ViewModel(), ErrorHandlerActions {

  private val _uiState = MutableStateFlow(MyAccountUiState())
  val uiState: StateFlow<MyAccountUiState> = _uiState.asStateFlow()

  private val _showToastEvent = MutableSharedFlow<UiText>()
  val showToastEvent: SharedFlow<UiText> = _showToastEvent.asSharedFlow()

  private val _navigateToLoginEvent = MutableSharedFlow<Unit>()
  val navigateToLoginEvent: SharedFlow<Unit> = _navigateToLoginEvent.asSharedFlow()

  fun getUserInfo() {
    viewModelScope.launch {
      getUserInfoUseCase()
        .onSuccess { userInfo ->
          _uiState.update { uiState ->
            uiState.copy(
              nickname = userInfo.nickname,
              email = userInfo.email,
              profileImageModel = userInfo.profileImageEntity?.toUiModel(),
            )
          }
        }.onFailure { exception ->
          handleException(exception, this@MyAccountViewModel)
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
}
