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
import us.wedemy.eggeum.android.domain.usecase.LogoutUseCase

@HiltViewModel
class SettingViewModel @Inject constructor(
  private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

  private val _navigateToLogoinEvent = MutableSharedFlow<Unit>(replay = 1)
  val navigateToLoginEvent: SharedFlow<Unit> = _navigateToLogoinEvent.asSharedFlow()

  fun logout() {
    viewModelScope.launch {
      logoutUseCase()
      _navigateToLogoinEvent.emit(Unit)
    }
  }
}
