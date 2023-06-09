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
import us.wedemy.eggeum.onboard.util.getMutableStateFlow

class RegisterNicknameViewModel(
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _inputNickname = savedStateHandle.getMutableStateFlow(KEY_NICKNAME, "")

  private val _enableRegisterNickname: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val enableRegisterNickname: StateFlow<Boolean> = _enableRegisterNickname.asStateFlow()

  fun setNickname(nickname: String) {
    _inputNickname.value = nickname
  }

  fun setButtonState(flag: Boolean) {
    _enableRegisterNickname.value = flag
  }

  companion object {
    private const val KEY_NICKNAME = "nickname"
  }
}