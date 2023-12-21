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
import us.wedemy.eggeum.android.main.model.ImageModel

@HiltViewModel
class CafeImageDetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  val cafeImages: ImageModel =
    requireNotNull(savedStateHandle.get<ImageModel>(CAFE_IMAGES)) { "cafeImages is required." }

  val currentPosition: Int =
    requireNotNull(savedStateHandle.get<Int>(CURRENT_POSITION)) { "currentPosition is required." }

  private companion object {
    private const val CAFE_IMAGES = "cafe_images"
    private const val CURRENT_POSITION = "current_position"
  }
}