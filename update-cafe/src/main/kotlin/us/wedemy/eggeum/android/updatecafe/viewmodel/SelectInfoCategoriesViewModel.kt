/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("unused")

package us.wedemy.eggeum.android.updatecafe.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import us.wedemy.eggeum.android.common.util.getMutableStateFlow

@HiltViewModel
class SelectInfoCategoriesViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
  private val _agreeToInfo = savedStateHandle.getMutableStateFlow(KEY_AGREE_TO_INFO, false)
  val agreeToInfo = _agreeToInfo.asStateFlow()

  private val _agreeToMenu = savedStateHandle.getMutableStateFlow(KEY_AGREE_TO_MENU, false)
  val agreeToMenu = _agreeToMenu.asStateFlow()

  fun setCbAgreeToInfo() {
    _agreeToInfo.value = !agreeToInfo.value

    if (_agreeToMenu.value) _agreeToMenu.value = !_agreeToInfo.value
  }

  fun setCbAgreeToMenu() {
    _agreeToMenu.value = !agreeToMenu.value

    if (_agreeToInfo.value) _agreeToInfo.value = !_agreeToMenu.value
  }

  private companion object {
    private const val KEY_AGREE_TO_INFO = "agree_to_info"
    private const val KEY_AGREE_TO_MENU = "agree_to_menu"
  }
}
