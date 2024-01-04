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
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {
  private val _navigateToUpdateCafeEvent = MutableSharedFlow<Unit>()
  val navigateToUpdateCafeEvent: SharedFlow<Unit> = _navigateToUpdateCafeEvent.asSharedFlow()

  fun navigateToUpdateCafe() {
    viewModelScope.launch {
      _navigateToUpdateCafeEvent.emit(Unit)
    }
  }
}
