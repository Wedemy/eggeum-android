/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import us.wedemy.eggeum.android.common.util.getMutableStateFlow

@HiltViewModel
class WithdrawViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
  private val _agreeToNotification = savedStateHandle.getMutableStateFlow(KEY_AGREE_TO_NOTIFICATION, false)
  val agreeToNotification = _agreeToNotification.asStateFlow()

  fun setCbWithdrawAgreeToNotification() {
    _agreeToNotification.value = !agreeToNotification.value
  }

  private companion object {
    private const val KEY_AGREE_TO_NOTIFICATION = "agree_to_notification"
  }
}