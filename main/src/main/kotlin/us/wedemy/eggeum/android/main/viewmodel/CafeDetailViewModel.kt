/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import us.wedemy.eggeum.android.main.model.CafeDetailModel

@HiltViewModel
class CafeDetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val cafeDetailModel: CafeDetailModel =
    requireNotNull(savedStateHandle.get<CafeDetailModel>(CAFE_DETAIL_MODEL)) { "cafeDetailInfo is required." }

  private companion object {
    private const val CAFE_DETAIL_MODEL = "cafe_detail_model"
  }
}