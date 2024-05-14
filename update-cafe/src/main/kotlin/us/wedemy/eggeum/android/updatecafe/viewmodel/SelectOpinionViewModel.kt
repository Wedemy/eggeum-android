/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.updatecafe.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import us.wedemy.eggeum.android.common.util.getMutableStateFlow

@HiltViewModel
class SelectOpinionViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
  private val _agreeToRequestCorrectionOfInfo = savedStateHandle.getMutableStateFlow(KEY_AGREE_TO_REQUEST_CORRECTION_OF_INFO, false)
  val agreeToRequestCorrectionOfInfo = _agreeToRequestCorrectionOfInfo.asStateFlow()

  fun setCbAgreeToRequestCorrectionOfInfo() {
    _agreeToRequestCorrectionOfInfo.value = !agreeToRequestCorrectionOfInfo.value
  }

  private companion object {
    private const val KEY_AGREE_TO_REQUEST_CORRECTION_OF_INFO = "agree_to_request_correction_of_info"
  }
}
