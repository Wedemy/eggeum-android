/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.onboard.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
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
import us.wedemy.eggeum.android.domain.model.login.SignUpRequestEntity
import us.wedemy.eggeum.android.domain.usecase.CheckNicknameExistUseCase
import us.wedemy.eggeum.android.domain.usecase.LogoutUseCase
import us.wedemy.eggeum.android.domain.usecase.SetAccessTokenUseCase
import us.wedemy.eggeum.android.domain.usecase.SetRefreshTokenUseCase
import us.wedemy.eggeum.android.domain.usecase.SignUpUseCase
import us.wedemy.eggeum.android.onboard.R

// TODO navigateToLoginEvent replay = 1 없어도 되는지 확인
@HiltViewModel
class OnBoardViewModel @Inject constructor(
  private val signUpUseCase: SignUpUseCase,
  private val checkNicknameExistUseCase: CheckNicknameExistUseCase,
  private val setAccessTokenUseCase: SetAccessTokenUseCase,
  private val setRefreshTokenUseCase: SetRefreshTokenUseCase,
  private val logoutUseCase: LogoutUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel(), ErrorHandlerActions {
  private val idToken: String =
    savedStateHandle[ID_TOKEN] ?: throw IllegalArgumentException("ID_TOKEN is missing from SavedStateHandle")

  private val _agreeToServiceTerms = savedStateHandle.getMutableStateFlow(KEY_AGREE_TO_SERVICE_TERMS, false)
  val agreeToServiceTerms = _agreeToServiceTerms.asStateFlow()

  private val _agreeToCollectPersonalInfo =
    savedStateHandle.getMutableStateFlow(KEY_AGREE_TO_COLLECT_PERSONAL_INFO, false)
  val agreeToCollectPersonalInfo = _agreeToCollectPersonalInfo.asStateFlow()

  private val _agreeToProvidePersonalInfoTo3rdParty =
    savedStateHandle.getMutableStateFlow(KEY_AGREE_TO_PROVIDE_PERSONAL_INFO_TO_3RD_PARTY, false)
  val agreeToProvidePersonalInfoTo3rdParty = _agreeToProvidePersonalInfoTo3rdParty.asStateFlow()

  private val _isOver14YearOld = savedStateHandle.getMutableStateFlow(KEY_IS_OVER_14_YEAR_OLD, false)
  val isOver14YearOld = _isOver14YearOld.asStateFlow()

  private val _agreeMarketing = savedStateHandle.getMutableStateFlow(AGREE_MARKETING, false)
  val agreeMarketing = _agreeMarketing.asStateFlow()

  private val _agreeToAllRequiredItems = savedStateHandle.getMutableStateFlow(KEY_AGREE_TO_REQUIRED_ITEMS, false)

  private val _nickname = savedStateHandle.getMutableStateFlow(KEY_NICKNAME, "")

  private val _nicknameState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_NICKNAME_STATE, EditTextState.Idle)
  val nicknameState = _nicknameState.asStateFlow()

  private val _navigateToMainEvent = MutableSharedFlow<Unit>()
  val navigateToMainEvent: SharedFlow<Unit> = _navigateToMainEvent.asSharedFlow()

  private val _showToastEvent = MutableSharedFlow<UiText>()
  val showToastEvent: SharedFlow<UiText> = _showToastEvent.asSharedFlow()

  private val _navigateToLoginEvent = MutableSharedFlow<Unit>()
  val navigateToLoginEvent: SharedFlow<Unit> = _navigateToLoginEvent.asSharedFlow()

  fun setCbAgreeToServiceTerms() {
    _agreeToServiceTerms.value = !agreeToServiceTerms.value
  }

  fun checkCbAgreeToServiceTerms() {
    _agreeToServiceTerms.value = true
  }

  fun setCbAgreeToCollectPersonalInfo() {
    _agreeToCollectPersonalInfo.value = !agreeToCollectPersonalInfo.value
  }

  fun checkCbAgreeToCollectPersonalInfo() {
    _agreeToCollectPersonalInfo.value = true
  }

  fun setCbAgreeToProvidePersonalInfoTo3rdParty() {
    _agreeToProvidePersonalInfoTo3rdParty.value = !agreeToProvidePersonalInfoTo3rdParty.value
  }

  fun checkCbAgreeToProvidePersonalInfoTo3rdParty() {
    _agreeToProvidePersonalInfoTo3rdParty.value = true
  }

  fun setCbOver14YearOld() {
    _isOver14YearOld.value = !isOver14YearOld.value
  }

  fun setCbWouldLikeToReceiveInfoAboutNewCafeAndEvents() {
    _agreeMarketing.value = !agreeMarketing.value
  }

  fun setAgreeToAllRequiredItems() {
    _agreeToAllRequiredItems.value = !_agreeToAllRequiredItems.value
    _agreeToServiceTerms.value = _agreeToAllRequiredItems.value
    _agreeToCollectPersonalInfo.value = _agreeToAllRequiredItems.value
    _agreeToProvidePersonalInfoTo3rdParty.value = _agreeToAllRequiredItems.value
    _isOver14YearOld.value = _agreeToAllRequiredItems.value
    _agreeMarketing.value = _agreeToAllRequiredItems.value
  }

  val enableRegisterAccount =
    combine(
      agreeToServiceTerms,
      agreeToCollectPersonalInfo,
      agreeToProvidePersonalInfoTo3rdParty,
      isOver14YearOld,
    ) { checks ->
      checks.all { it }
    }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false,
      )

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
      else -> {
        viewModelScope.launch {
          checkNicknameExistUseCase(nickname)
            .onSuccess { flag ->
              if (!flag) {
                _nicknameState.value = EditTextState.Success
              } else {
                _nicknameState.value = EditTextState.Error(TextInputError.ALREADY_EXIST)
              }
            }.onFailure { exception ->
              handleException(exception, this@OnBoardViewModel)
            }
        }
      }
    }
  }

  fun signUp() {
    viewModelScope.launch {
      signUpUseCase(
        SignUpRequestEntity(
          agreemMarketing = _agreeMarketing.value,
          idToken = idToken,
          nickname = _nickname.value,
        ),
      ).onSuccess { signUpEntity ->
        setAccessTokenUseCase(signUpEntity.accessToken)
        setRefreshTokenUseCase(signUpEntity.refreshToken)
        _navigateToMainEvent.emit(Unit)
      }.onFailure { exception ->
        handleException(exception, this@OnBoardViewModel)
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

  fun containsWhitespace(text: String): Boolean {
    return text.matches(Regex(".*\\s.*"))
  }

  override fun handleRefreshTokenExpired() {
    viewModelScope.launch {
      logoutUseCase()
      _navigateToLoginEvent.emit(Unit)
    }
  }

  private companion object {
    private const val ID_TOKEN = "id_token"
    private const val KEY_AGREE_TO_SERVICE_TERMS = "agree_to_service_terms"
    private const val KEY_AGREE_TO_COLLECT_PERSONAL_INFO = "agree_to_collect_personal_info"
    private const val KEY_AGREE_TO_PROVIDE_PERSONAL_INFO_TO_3RD_PARTY = "agree_to_provide_personal_info_to_3rd_party"
    private const val KEY_IS_OVER_14_YEAR_OLD = "is_over_14_year_old"
    private const val AGREE_MARKETING = "agree_marketing"
    private const val KEY_AGREE_TO_REQUIRED_ITEMS = "agree_to_all_required_items"
    private const val KEY_NICKNAME = "nickname"
    private const val KEY_NICKNAME_STATE = "nickname_state"
  }
}
