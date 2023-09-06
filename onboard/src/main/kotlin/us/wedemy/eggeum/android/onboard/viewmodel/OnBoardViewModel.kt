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
import timber.log.Timber
import us.wedemy.eggeum.android.common.util.EditTextState
import us.wedemy.eggeum.android.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.android.common.util.TextInputError
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.domain.usecase.GetSignUpBodyUseCase

@HiltViewModel
class OnBoardViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val getSignUpBodyUseCase: GetSignUpBodyUseCase,
) : ViewModel() {
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

  private val _wouldLikeToReceiveInfoAboutNewCafeAndEvents =
    savedStateHandle.getMutableStateFlow(KEY_WOULD_LIKE_TO_RECEIVE_INFO_ABOUT_NEW_CAFE_EVENTS, false)
  val wouldLikeToReceiveInfoAboutNewCafeAndEvents = _wouldLikeToReceiveInfoAboutNewCafeAndEvents.asStateFlow()

  private val _agreeToAllRequiredItems = savedStateHandle.getMutableStateFlow(KEY_AGREE_TO_REQUIRED_ITEMS, false)

  private val _nickname = savedStateHandle.getMutableStateFlow(KEY_NICKNAME, "")

  private val _nicknameState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_NICKNAME_STATE, EditTextState.Idle)
  val nicknameState = _nicknameState.asStateFlow()

  private val _navigateToMainEvent = MutableSharedFlow<Unit>()
  val navigateToMainEvent: SharedFlow<Unit> = _navigateToMainEvent.asSharedFlow()

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()

  fun setCbAgreeToServiceTerms() {
    _agreeToServiceTerms.value = !agreeToServiceTerms.value
  }

  fun setCbAgreeToCollectPersonalInfo() {
    _agreeToCollectPersonalInfo.value = !agreeToCollectPersonalInfo.value
  }

  fun setCbAgreeToProvidePersonalInfoTo3rdParty() {
    _agreeToProvidePersonalInfoTo3rdParty.value = !agreeToProvidePersonalInfoTo3rdParty.value
  }

  fun setCbOver14YearOld() {
    _isOver14YearOld.value = !isOver14YearOld.value
  }

  fun setCbWouldLikeToReceiveInfoAboutNewCafeAndEvents() {
    _wouldLikeToReceiveInfoAboutNewCafeAndEvents.value = !wouldLikeToReceiveInfoAboutNewCafeAndEvents.value
  }

  fun setAgreeToAllRequiredItems() {
    _agreeToAllRequiredItems.value = !_agreeToAllRequiredItems.value
    _agreeToServiceTerms.value = _agreeToAllRequiredItems.value
    _agreeToCollectPersonalInfo.value = _agreeToAllRequiredItems.value
    _agreeToProvidePersonalInfoTo3rdParty.value = _agreeToAllRequiredItems.value
    _isOver14YearOld.value = _agreeToAllRequiredItems.value
    _wouldLikeToReceiveInfoAboutNewCafeAndEvents.value = _agreeToAllRequiredItems.value
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
      else -> {
        _nicknameState.value = EditTextState.Success
      }
    }
  }

  fun getSignUpBody(
    agreeMarketing: Boolean,
    idToken: String,
    nickname: String,
  ) {
    viewModelScope.launch {
      val result = getSignUpBodyUseCase.execute(agreeMarketing, idToken, nickname)
      when {
        result.isSuccess && result.getOrNull() != null -> {
          val signUpBody = result.getOrNull()
          Timber.d("$signUpBody")
          _navigateToMainEvent.emit(Unit)
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
    private const val KEY_AGREE_TO_SERVICE_TERMS = "agree_to_service_terms"
    private const val KEY_AGREE_TO_COLLECT_PERSONAL_INFO = "agree_to_collect_personal_info"
    private const val KEY_AGREE_TO_PROVIDE_PERSONAL_INFO_TO_3RD_PARTY = "agree_to_provide_personal_info_to_3rd_party"
    private const val KEY_IS_OVER_14_YEAR_OLD = "is_over_14_year_old"
    private const val KEY_WOULD_LIKE_TO_RECEIVE_INFO_ABOUT_NEW_CAFE_EVENTS =
      "would_like_to_receive_info_about_new_cafe_and_events"
    private const val KEY_AGREE_TO_REQUIRED_ITEMS = "agree_to_all_required_items"
    private const val KEY_NICKNAME = "nickname"
    private const val KEY_NICKNAME_STATE = "nickname_state"
  }
}