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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.main.model.CafeDetailModel

@HiltViewModel
class CafeDetailViewModel @Inject constructor() : ViewModel() {
  private val _cafeDetailInfo = MutableStateFlow(CafeDetailModel())
  val cafeDetailInfo: StateFlow<CafeDetailModel> = _cafeDetailInfo.asStateFlow()

  private val _navigateToUpdateCafeEvent = MutableSharedFlow<Unit>()
  val navigateToUpdateCafeEvent: SharedFlow<Unit> = _navigateToUpdateCafeEvent.asSharedFlow()

  fun setCafeDetailInfo(cafeDetailInfo: CafeDetailModel) {
    _cafeDetailInfo.value = cafeDetailInfo
  }

  fun navigateToUpdateCafe() {
    viewModelScope.launch {
      _navigateToUpdateCafeEvent.emit(Unit)
    }
  }
}
