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
import timber.log.Timber
import us.wedemy.eggeum.android.common.util.EditTextState
import us.wedemy.eggeum.android.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.android.common.util.TextInputError
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.domain.model.FileEntity
import us.wedemy.eggeum.android.domain.model.ProfileImageEntity
import us.wedemy.eggeum.android.domain.model.user.UpdateUserInfoEntity
import us.wedemy.eggeum.android.domain.usecase.CheckNicknameExistUseCase
import us.wedemy.eggeum.android.domain.usecase.UpdateUserInfoUseCase
import us.wedemy.eggeum.android.domain.usecase.UploadImageFileUseCase
import us.wedemy.eggeum.android.main.mapper.toEntity
import us.wedemy.eggeum.android.main.model.UserInfoModel

@HiltViewModel
class EditMyInfoViewModel @Inject constructor(
  private val uploadImageFileUseCase: UploadImageFileUseCase,
  private val updateUserInfoUseCase: UpdateUserInfoUseCase,
  private val checkNicknameExistUseCase: CheckNicknameExistUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
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

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()

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
      val result = uploadImageFileUseCase(profileImageUri)
      when {
        result.isSuccess && result.getOrNull() != null -> {
          val profileImageFile = result.getOrNull()!!
          updateUserProfileAndNickname(
            FileEntity(
              uploadFileId = profileImageFile.uploadFileId,
              url = profileImageFile.url,
            ),
          )
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
    _newProfileImageUri.value = uri
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
        viewModelScope.launch {
          val result = checkNicknameExistUseCase(nickname)
          when {
            result.isSuccess && result.getOrNull() != null -> {
              if (result.getOrNull() == false) {
                _nicknameState.value = EditTextState.Success
              } else {
                _nicknameState.value = EditTextState.Error(TextInputError.ALREADY_EXIST)
              }
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
    }
  }

  fun updateUserNickname() {
    viewModelScope.launch {
      if (newProfileImageUri.value != null && newProfileImageUri.value != userInfo.value.profileImageModel?.files?.get(0)?.url) {
        getUploadFileId(newProfileImageUri.value!!)
      } else {
        val result = updateUserInfoUseCase(
          UpdateUserInfoEntity(
            nickname = _nickname.value,
            profileImageEntity = userInfo.value.profileImageModel?.toEntity(),
          ),
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
  }

  private fun updateUserProfileAndNickname(file: FileEntity) {
    viewModelScope.launch {
      val result = updateUserInfoUseCase(
        UpdateUserInfoEntity(
          nickname = _nickname.value,
          profileImageEntity = ProfileImageEntity(files = listOf(file)),
        ),
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
