/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.domain.usecase.GetAccessTokenUseCase

@HiltViewModel
class IntroViewModel @Inject constructor(
  private val getAccessTokenUseCase: GetAccessTokenUseCase,
) : ViewModel() {

  private val _navigateToMainEvent = MutableSharedFlow<Unit>(replay = 1)
  val navigateToMainEvent: SharedFlow<Unit> = _navigateToMainEvent.asSharedFlow()

  private val _navigateToLoginEvent = MutableSharedFlow<Unit>(replay = 1)
  val navigateToLoginEvent: SharedFlow<Unit> = _navigateToLoginEvent.asSharedFlow()

  init {
    getAccessToken()
  }

  private fun getAccessToken() {
    viewModelScope.launch {
      val accessToken = getAccessTokenUseCase.execute()
      if (accessToken.isEmpty()) {
        _navigateToLoginEvent.emit(Unit)
      } else {
        _navigateToMainEvent.emit(Unit)
      }
    }
  }
}
