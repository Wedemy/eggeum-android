/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.onboard.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import us.wedemy.eggeum.common.util.getMutableStateFlow

class RegisterAccountViewModel(
  savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private val _agreeToServiceTerms = savedStateHandle.getMutableStateFlow(KEY_CHECK_BOX_1, false)
  val agreeToServiceTerms: StateFlow<Boolean> = _agreeToServiceTerms.asStateFlow()

  private val _agreeToCollectPersonalInfo = savedStateHandle.getMutableStateFlow(KEY_CHECK_BOX_2, false)
  val agreeToCollectPersonalInfo: StateFlow<Boolean> = _agreeToCollectPersonalInfo.asStateFlow()

  private val _agreeToProvidePersonalInfoTo3rdParty = savedStateHandle.getMutableStateFlow(KEY_CHECK_BOX_3, false)
  val agreeToProvidePersonalInfoTo3rdParty: StateFlow<Boolean> = _agreeToProvidePersonalInfoTo3rdParty.asStateFlow()

  private val _over14YearOld = savedStateHandle.getMutableStateFlow(KEY_CHECK_BOX_4, false)
  val over14YearOld: StateFlow<Boolean> = _over14YearOld.asStateFlow()

  private val _wouldLikeToReceiveInfoAboutNewCafeAndEvents =
    savedStateHandle.getMutableStateFlow(KEY_CHECK_BOX_5, false)
  val wouldLikeToReceiveInfoAboutNewCafeAndEvents: StateFlow<Boolean> =
    _wouldLikeToReceiveInfoAboutNewCafeAndEvents.asStateFlow()

  private val _agreeToAllRequiredItems = savedStateHandle.getMutableStateFlow(KEY_CHECK_BOX_6, false)
  val agreeToAllRequiredItems: StateFlow<Boolean> = _agreeToAllRequiredItems.asStateFlow()

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
    _over14YearOld.value = !over14YearOld.value
  }

  fun setCbWouldLikeToReceiveInfoAboutNewCafeAndEvents() {
    _wouldLikeToReceiveInfoAboutNewCafeAndEvents.value = !wouldLikeToReceiveInfoAboutNewCafeAndEvents.value
  }

  fun setAgreeToAllRequiredItems() {
    _agreeToServiceTerms.value = agreeToAllRequiredItems.value
    _agreeToCollectPersonalInfo.value = agreeToAllRequiredItems.value
    _agreeToProvidePersonalInfoTo3rdParty.value = agreeToAllRequiredItems.value
    _over14YearOld.value = agreeToAllRequiredItems.value
    _agreeToAllRequiredItems.value = !agreeToAllRequiredItems.value
  }

  val enableRegisterAccount: StateFlow<Boolean> = combine(
    agreeToServiceTerms,
    agreeToCollectPersonalInfo,
    agreeToProvidePersonalInfoTo3rdParty,
    over14YearOld,
  ) { checks: Array<Boolean> ->
    checks.all { it }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

  companion object {
    private const val KEY_CHECK_BOX_1 = "check_box_1"
    private const val KEY_CHECK_BOX_2 = "check_box_2"
    private const val KEY_CHECK_BOX_3 = "check_box_3"
    private const val KEY_CHECK_BOX_4 = "check_box_4"
    private const val KEY_CHECK_BOX_5 = "check_box_5"
    private const val KEY_CHECK_BOX_6 = "check_box_6"
  }
}
