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
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.domain.usecase.WithdrawUseCase

@HiltViewModel
class WithdrawViewModel @Inject constructor(
  private val withdrawUseCase: WithdrawUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _agreeToWithdraw = savedStateHandle.getMutableStateFlow(KEY_AGREE_TO_NOTIFICATION, false)
  val agreeToWithdraw = _agreeToWithdraw.asStateFlow()

  private val _navigateToLoginEvent = MutableSharedFlow<Unit>(replay = 1)
  val navigateToLoginEvent: SharedFlow<Unit> = _navigateToLoginEvent.asSharedFlow()

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()

  fun setAgreeToWithdraw() {
    _agreeToWithdraw.value = !agreeToWithdraw.value
  }

  fun withdraw() {
    viewModelScope.launch {
      val result = withdrawUseCase.execute()
      when {
        result.isSuccess -> {
          _navigateToLoginEvent.emit(Unit)
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
    private const val KEY_AGREE_TO_NOTIFICATION = "agree_to_notification"
  }
}
