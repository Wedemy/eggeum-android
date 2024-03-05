/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.util.ErrorHandlerActions
import us.wedemy.eggeum.android.common.util.UiText
import us.wedemy.eggeum.android.common.util.handleException
import us.wedemy.eggeum.android.domain.model.login.LoginRequestEntity
import us.wedemy.eggeum.android.domain.usecase.LoginUseCase
import us.wedemy.eggeum.android.domain.usecase.SetAccessTokenUseCase
import us.wedemy.eggeum.android.domain.usecase.SetRefreshTokenUseCase

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val loginUseCase: LoginUseCase,
  private val setAccessTokenUseCase: SetAccessTokenUseCase,
  private val setRefreshTokenUseCase: SetRefreshTokenUseCase,
) : ViewModel(), ErrorHandlerActions {

  private val _navigateToMainEvent = MutableSharedFlow<Unit>(replay = 1)
  val navigateToMainEvent: SharedFlow<Unit> = _navigateToMainEvent.asSharedFlow()

  private val _navigateToOnBoardingEvent = MutableSharedFlow<Unit>(replay = 1)
  val navigateToOnBoardingEvent: SharedFlow<Unit> = _navigateToOnBoardingEvent.asSharedFlow()

  private val _showToastEvent = MutableSharedFlow<UiText>()
  val showToastEvent: SharedFlow<UiText> = _showToastEvent.asSharedFlow()

  fun login(idToken: String) {
    viewModelScope.launch {
      loginUseCase(LoginRequestEntity(idToken = idToken))
        .onSuccess { loginEntity ->
          setAccessTokenUseCase(loginEntity.accessToken)
          setRefreshTokenUseCase(loginEntity.refreshToken)
          _navigateToMainEvent.emit(Unit)
        }
        .onFailure { exception ->
          handleException(exception, this@LoginViewModel)
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
    viewModelScope.launch {
      _navigateToOnBoardingEvent.emit(Unit)
    }
  }

  override fun handleRefreshTokenExpired() {
    //
  }
}
