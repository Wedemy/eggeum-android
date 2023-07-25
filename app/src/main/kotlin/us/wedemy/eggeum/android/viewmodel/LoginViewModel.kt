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
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.domain.usecase.GetLoginBodyUseCase

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val getLoginBodyUseCase: GetLoginBodyUseCase,
) : ViewModel() {
  fun getLoginBody(idToken: String) {
    viewModelScope.launch {
      val result = getLoginBodyUseCase.execute(idToken)
      when {
        result.isSuccess && result.getOrNull() != null -> {
          val loginBody = result.getOrNull()
          Timber.d("$loginBody")
        }
        result.isSuccess && result.getOrNull() == null -> {
          Timber.e("Request succeeded but data validation failed.")
        }
        result.isFailure -> {
          val exception = result.exceptionOrNull()
          Timber.d(exception)
        }
      }
    }
  }
}