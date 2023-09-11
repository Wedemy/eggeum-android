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
import us.wedemy.eggeum.android.domain.usecase.CheckNicknameExistUseCase
import us.wedemy.eggeum.android.domain.usecase.GetSignUpBodyUseCase

@HiltViewModel
class OnBoardViewModel @Inject constructor(
  private val getSignUpBodyUseCase: GetSignUpBodyUseCase,
  private val checkNicknameExistUseCase: CheckNicknameExistUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
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
      else -> {
        viewModelScope.launch {
          val result = checkNicknameExistUseCase.execute(nickname)
          when {
            result.isSuccess && result.getOrNull() != null -> {
              if (result.getOrNull() == true) {
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

  fun getSignUpBody() {
    viewModelScope.launch {
      val result = getSignUpBodyUseCase.execute(
        _agreeMarketing.value,
        idToken,
        _nickname.value,
      )
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
