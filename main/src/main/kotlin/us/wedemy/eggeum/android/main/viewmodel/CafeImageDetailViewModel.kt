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
import kotlinx.coroutines.flow.StateFlow
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.main.model.ImageModel

@HiltViewModel
class CafeImageDetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  val cafeImages: ImageModel =
    requireNotNull(savedStateHandle.get<ImageModel>(CAFE_IMAGES)) { "cafeImages is required." }

  private val _currentPosition = savedStateHandle.getMutableStateFlow(CURRENT_POSITION, 0)
  val currentPosition: StateFlow<Int> = _currentPosition.asStateFlow()

  fun updateCurrentPosition(position: Int) {
    _currentPosition.value = position
  }

  private companion object {
    private const val CAFE_IMAGES = "cafe_images"
    private const val CURRENT_POSITION = "current_position"
  }
}