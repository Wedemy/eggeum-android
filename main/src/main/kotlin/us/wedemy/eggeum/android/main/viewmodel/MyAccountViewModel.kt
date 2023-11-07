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
import timber.log.Timber
import us.wedemy.eggeum.android.domain.usecase.GetUserInfoUseCase
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
) : ViewModel() {

  private val _uiState = MutableStateFlow(MyAccountUiState())
  val uiState: StateFlow<MyAccountUiState> = _uiState.asStateFlow()

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()

  fun getUserInfo() {
    viewModelScope.launch {
      val result = getUserInfoUseCase()
      when {
        result.isSuccess && result.getOrNull() != null -> {
          val userInfo = result.getOrNull()!!
          _uiState.update { uiState ->
            uiState.copy(
              nickname = userInfo.nickname,
              email = userInfo.email,
              // profileImageUrl = userInfoBody.profileImageEntity?.let { it.files[0].url },
              profileImageModel = userInfo.profileImageEntity?.toUiModel()
            )
          }
          // Timber.d("${userInfo.profileImageEntity?.files?.get(0)?.url}")
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
