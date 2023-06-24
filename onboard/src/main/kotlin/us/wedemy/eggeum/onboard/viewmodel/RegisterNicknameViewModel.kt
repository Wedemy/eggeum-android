/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.onboard.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import us.wedemy.eggeum.common.util.EditTextState
import us.wedemy.eggeum.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.common.util.getMutableStateFlow
import us.wedemy.eggeum.onboard.R

class RegisterNicknameViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
  private val _inputNickname = savedStateHandle.getMutableStateFlow(KEY_NICKNAME, "")

  private val _inputNicknameState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_NICKNAME_STATE, EditTextState.Idle)
  val inputNicknameState = _inputNicknameState.asStateFlow()

  fun handleNicknameValidation(nickname: String) {
    when {
      nickname.isEmpty() -> {
        _inputNicknameState.value = EditTextState.Error(R.string.empty_error_text)
      }
      nickname.length < 2 -> {
        _inputNicknameState.value = EditTextState.Error(R.string.min_length_error_text)
      }
      else -> {
        _inputNickname.value = nickname
        _inputNicknameState.value = EditTextState.Success
      }
    }
  }

  private companion object {
    private const val KEY_NICKNAME = "nickname"
    private const val KEY_NICKNAME_STATE = "nickname_state"
  }
}
