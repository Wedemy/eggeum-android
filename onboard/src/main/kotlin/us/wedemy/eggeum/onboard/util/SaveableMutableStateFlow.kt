/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.onboard.util

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow

class SaveableMutableStateFlow<T>(
  private val savedStateHandle: SavedStateHandle,
  private val key: String,
  initialValue: T,
) {
  private val state: StateFlow<T> = savedStateHandle.getStateFlow(key, initialValue)
  var value: T
    get() = state.value
    set(value) {
      savedStateHandle[key] = value
    }

  fun asStateFlow(): StateFlow<T> = state
}

fun <T> SavedStateHandle.getMutableStateFlow(
  key: String,
  initialValue: T,
): SaveableMutableStateFlow<T> =
  SaveableMutableStateFlow(this, key, initialValue)
