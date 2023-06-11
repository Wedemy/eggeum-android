/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.onboard.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import us.wedemy.eggeum.onboard.R
import us.wedemy.eggeum.onboard.util.EditTextState
import us.wedemy.eggeum.onboard.util.SaveableMutableStateFlow
import us.wedemy.eggeum.onboard.util.getMutableStateFlow

class RegisterNicknameViewModel(
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _inputNickname = savedStateHandle.getMutableStateFlow(KEY_NICKNAME, "")

  private val _inputNicknameState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_NICKNAME_STATE, EditTextState.Idle)
  val inputNicknameState: StateFlow<EditTextState> = _inputNicknameState.asStateFlow()

  private val _enableRegisterNickname: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val enableRegisterNickname: StateFlow<Boolean> = _enableRegisterNickname.asStateFlow()

  fun handleNicknameValidation(nickname: String) {
    when {
      nickname.isEmpty() -> {
        if (_inputNicknameState.value != EditTextState.Idle) {
          _inputNicknameState.value = EditTextState.Error(R.string.empty_error_text)
        }
        setEnableRegisterNicknameState(false)
      }
      nickname.length < 2 -> {
        _inputNicknameState.value = EditTextState.Error(R.string.min_length_error_text)
        setEnableRegisterNicknameState(false)
      }
      else -> {
        _inputNickname.value = nickname
        _inputNicknameState.value = EditTextState.Success
        setEnableRegisterNicknameState(true)
      }
    }
  }

  private fun setEnableRegisterNicknameState(flag: Boolean) {
    _enableRegisterNickname.value = flag
  }

  companion object {
    private const val KEY_NICKNAME = "nickname"
    private const val KEY_NICKNAME_STATE = "nickname_state"
  }
}
