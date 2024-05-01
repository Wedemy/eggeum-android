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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.util.EditTextState
import us.wedemy.eggeum.android.common.util.ErrorHandlerActions
import us.wedemy.eggeum.android.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.android.common.util.TextInputError
import us.wedemy.eggeum.android.common.util.UiText
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.common.util.handleException
import us.wedemy.eggeum.android.domain.model.FileEntity
import us.wedemy.eggeum.android.domain.model.ProfileImageEntity
import us.wedemy.eggeum.android.domain.model.user.UpdateUserInfoEntity
import us.wedemy.eggeum.android.domain.model.user.UpdateUserNicknameEntity
import us.wedemy.eggeum.android.domain.usecase.CheckNicknameExistUseCase
import us.wedemy.eggeum.android.domain.usecase.LogoutUseCase
import us.wedemy.eggeum.android.domain.usecase.UpdateUserNicknameUseCase
import us.wedemy.eggeum.android.domain.usecase.UpdateUserProfileAndNicknameUseCase
import us.wedemy.eggeum.android.domain.usecase.UploadImageFileUseCase
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.model.UserInfoModel

@HiltViewModel
class EditMyInfoViewModel @Inject constructor(
  private val uploadImageFileUseCase: UploadImageFileUseCase,
  private val updateUserProfileAndNicknameUseCase: UpdateUserProfileAndNicknameUseCase,
  private val updateUserNicknameUseCase: UpdateUserNicknameUseCase,
  private val checkNicknameExistUseCase: CheckNicknameExistUseCase,
  private val logoutUseCase: LogoutUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel(), ErrorHandlerActions {
  private val _userInfo = savedStateHandle.getMutableStateFlow(KEY_USER_INFO, UserInfoModel())
  val userInfo: StateFlow<UserInfoModel> = _userInfo.asStateFlow()

  private val _newProfileImageUri =
    savedStateHandle.getMutableStateFlow(KEY_PROFILE_IMAGE_URL, userInfo.value.profileImageModel?.files?.get(0)?.url)
  val newProfileImageUri = _newProfileImageUri.asStateFlow()

  private val _nickname = savedStateHandle.getMutableStateFlow(KEY_NICKNAME, userInfo.value.nickname)

  private val _nicknameState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_NICKNAME_STATE, EditTextState.Idle)
  val nicknameState = _nicknameState.asStateFlow()

  private val _userInfoUpdateSuccessEvent = MutableSharedFlow<Unit>(replay = 1)
  val userInfoUpdateSuccessEvent: SharedFlow<Unit> = _userInfoUpdateSuccessEvent.asSharedFlow()

  private val _showToastEvent = MutableSharedFlow<UiText>()
  val showToastEvent: SharedFlow<UiText> = _showToastEvent.asSharedFlow()

  private val _navigateToLoginEvent = MutableSharedFlow<Unit>(replay = 1)
  val navigateToLoginEvent: SharedFlow<Unit> = _navigateToLoginEvent.asSharedFlow()

  val enableUpdateUserInfo =
    combine(
      nicknameState,
      newProfileImageUri,
    ) { nickname, uri ->
      nickname == EditTextState.Success || uri != (userInfo.value.profileImageModel?.files?.get(0)?.url)
    }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false,
      )

  private fun getUploadFileId(profileImageUri: String) {
    viewModelScope.launch {
      uploadImageFileUseCase(profileImageUri)
        .onSuccess { profileImageFile ->
          updateUserProfileAndNickname(
            FileEntity(
              uploadFileId = profileImageFile.uploadFileId,
              url = profileImageFile.url,
            ),
          )
        }
        .onFailure { exception ->
          handleException(exception, this@EditMyInfoViewModel)
        }
    }
  }

  fun setProfileImageUri(uri: String) {
    _newProfileImageUri.value = uri
  }

  fun setNickname(nickname: String) {
    _nickname.value = nickname
  }

  fun handleNicknameValidation(nickname: String) {
    setNickname(nickname)
    when {
      nickname.isEmpty() -> {
        _nicknameState.value = EditTextState.Error(TextInputError.EMPTY)
      }
      nickname.length < 2 -> {
        _nicknameState.value = EditTextState.Error(TextInputError.TOO_SHORT)
      }
      containsWhitespace(nickname) -> {
        _nicknameState.value = EditTextState.Error(TextInputError.CONTAINS_WHITESPACE)
      }
      containsInvalidCharacter(nickname) -> {
        _nicknameState.value = EditTextState.Error(TextInputError.INVALID_CHARACTER)
      }
      else -> {
        viewModelScope.launch {
          checkNicknameExistUseCase(nickname)
            .onSuccess { flag ->
              if (!flag) {
                _nicknameState.value = EditTextState.Success
              } else {
                _nicknameState.value = EditTextState.Error(TextInputError.ALREADY_EXIST)
              }
            }
            .onFailure { exception ->
              handleException(exception, this@EditMyInfoViewModel)
            }
        }
      }
    }
  }

  fun updateUserInfo() {
    viewModelScope.launch {
      if (newProfileImageUri.value != null && newProfileImageUri.value != userInfo.value.profileImageModel?.files?.get(0)?.url) {
        getUploadFileId(newProfileImageUri.value!!)
      } else {
        updateUserNicknameUseCase(UpdateUserNicknameEntity(nickname = _nickname.value))
          .onSuccess {
            _userInfoUpdateSuccessEvent.emit(Unit)
          }.onFailure { exception ->
            handleException(exception, this@EditMyInfoViewModel)
          }
      }
    }
  }

  private fun updateUserProfileAndNickname(file: FileEntity) {
    viewModelScope.launch {
      updateUserProfileAndNicknameUseCase(
        UpdateUserInfoEntity(
          nickname = _nickname.value,
          profileImageEntity = ProfileImageEntity(files = listOf(file)),
        ),
      ).onSuccess {
        _userInfoUpdateSuccessEvent.emit(Unit)
      }.onFailure { exception ->
        handleException(exception, this@EditMyInfoViewModel)
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

  private fun containsWhitespace(text: String): Boolean {
    return text.matches(Regex(".*\\s.*"))
  }

  private fun containsInvalidCharacter(text: String): Boolean {
    return !text.matches(Regex("^[a-zA-Z가-힣0-9]{2,20}$"))
  }

  override fun handleRefreshTokenExpired() {
    viewModelScope.launch {
      logoutUseCase()
      _navigateToLoginEvent.emit(Unit)
    }
  }

  private companion object {
    private const val KEY_USER_INFO = "user_info"
    private const val KEY_NICKNAME = "nickname"
    private const val KEY_PROFILE_IMAGE_URL = "profile_image_url"
    private const val KEY_NICKNAME_STATE = "nickname_state"
  }
}
