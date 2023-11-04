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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.common.util.EditTextState
import us.wedemy.eggeum.android.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.android.common.util.TextInputError
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.domain.model.user.UpdateUserInfoEntity
import us.wedemy.eggeum.android.domain.usecase.UpdateUserInfoUseCase
import us.wedemy.eggeum.android.domain.usecase.UploadImageFileUseCase
import us.wedemy.eggeum.android.main.ui.item.UserInfo

// 프로필 사진과 닉네임 둘다 변경해야만 버튼이 활성화 되는 것은 아님
@Suppress("unused")
@HiltViewModel
class EditMyInfoViewModel @Inject constructor(
  private val uploadImageFileUseCase: UploadImageFileUseCase,
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

  private val _userInfoUpdateSuccessEvent = MutableSharedFlow<Unit>(replay = 1)
  val userInfoUpdateSuccessEvent: SharedFlow<Unit> = _userInfoUpdateSuccessEvent.asSharedFlow()

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()

  fun getUploadFileId(profileImageUri: String) {
    viewModelScope.launch {
      val result = uploadImageFileUseCase(profileImageUri)
      when {
        result.isSuccess && result.getOrNull() != null -> {
          Timber.d("${result.getOrNull()}")
        }
        result.isSuccess && result.getOrNull() == null -> {
          Timber.e("Request succeeded but data validation failed.")
        }
        result.isFailure -> {
          val exception = result.exceptionOrNull()
          Timber.d(exception)
          _showToastEvent.emit(exception?.message ?: "Unknown Error Occured")
        }
      }
    }
  }

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
      val result = updateUserInfoUseCase(
        UpdateUserInfoEntity(nickname = _nickname.value)
      )
      when {
        result.isSuccess && result.getOrNull() != null -> {
          _userInfoUpdateSuccessEvent.emit(Unit)
        }
        result.isSuccess && result.getOrNull() == null -> {
          Timber.e("Request succeeded but data validation failed.")
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
    private const val KEY_USER_INFO = "user_info"
    private const val KEY_NICKNAME = "nickname"
    private const val KEY_PROFILE_IMAGE_URL = "profile_image_url"
    private const val KEY_NICKNAME_STATE = "nickname_state"
  }
}
