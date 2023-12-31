/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import us.wedemy.eggeum.android.main.model.CafeDetailModel

@HiltViewModel
class CafeDetailViewModel @Inject constructor() : ViewModel() {
  private val _cafeDetailInfo = MutableStateFlow(CafeDetailModel())
  val cafeDetailInfo: StateFlow<CafeDetailModel> = _cafeDetailInfo.asStateFlow()

  fun setCafeDetailInfo(cafeDetailInfo: CafeDetailModel) {
    _cafeDetailInfo.value = cafeDetailInfo
  }
}
