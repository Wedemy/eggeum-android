/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.onboard.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OnboardViewModelFactory(private val savedStateHandle: SavedStateHandle) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return when {
      modelClass.isAssignableFrom(RegisterAccountViewModel::class.java) -> {
        RegisterAccountViewModel(savedStateHandle) as T
      }
      modelClass.isAssignableFrom(RegisterNicknameViewModel::class.java) -> {
        RegisterNicknameViewModel(savedStateHandle) as T
      }
      else -> {
        throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
      }
    }
  }
}
