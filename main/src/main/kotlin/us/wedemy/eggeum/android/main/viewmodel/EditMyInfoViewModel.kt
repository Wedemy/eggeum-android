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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.util.EditTextState
import us.wedemy.eggeum.android.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.android.common.util.TextInputError
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.domain.usecase.UpdateUserInfoUseCase
import us.wedemy.eggeum.android.main.ui.item.UserInfo

@HiltViewModel
class EditMyInfoViewModel @Inject constructor(
  private val updateUserInfoUseCase: UpdateUserInfoUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _userInfo = savedStateHandle.getMutableStateFlow(KEY_USER_INFO, UserInfo())
  val userInfo: StateFlow<UserInfo> = _userInfo.asStateFlow()

  private val _profileImageUri = savedStateHandle.getMutableStateFlow(KEY_PROFILE_IMAGE_URL, "")
  val profileImageUri = _profileImageUri.asStateFlow()

  private val _nickname = savedStateHandle.getMutableStateFlow(KEY_NICKNAME, "")

  private val _nicknameState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_NICKNAME_STATE, EditTextState.Idle)
  val nicknameState = _nicknameState.asStateFlow()

  fun setProfileImageUri(uri: String) {
    _profileImageUri.value = uri
  }

  fun setNickname(nickname: String) {
    _nickname.value = nickname
  }

  fun handleNicknameValidation(nickname: String) {
    setNickname(nickname)
    when {
      _nickname.value.isEmpty() -> {
        _nicknameState.value = EditTextState.Error(TextInputError.EMPTY)
      }
      _nickname.value.length < 2 -> {
        _nicknameState.value = EditTextState.Error(TextInputError.TOO_SHORT)
      }
      else -> {
        _nicknameState.value = EditTextState.Success
      }
    }
  }

  fun updateUserInfo() {
    viewModelScope.launch {
      // val result = updateUserInfoUseCase.execute()
    }
  }

  private companion object {
    private const val KEY_USER_INFO = "user_info"
    private const val KEY_NICKNAME = "nickname"
    private const val KEY_PROFILE_IMAGE_URL = "profile_image_url"
    private const val KEY_NICKNAME_STATE = "nickname_state"
  }
}
