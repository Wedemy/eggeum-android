/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.registercafe.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import us.wedemy.eggeum.common.util.EditTextState
import us.wedemy.eggeum.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.common.util.Success
import us.wedemy.eggeum.common.util.getMutableStateFlow
import us.wedemy.eggeum.registercafe.item.CafeImageItem

class RegisterCafeViewModel(
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _cafeImages = savedStateHandle.getMutableStateFlow(KEY_CAFE_IMAGE_URL_LIST, emptyList<CafeImageItem>())
  val cafeImages: StateFlow<List<CafeImageItem>> = _cafeImages.asStateFlow()

  private val _inputCafeName = savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME, "")

  private val _inputCafeAddress = savedStateHandle.getMutableStateFlow(KEY_CAFE_ADDRESS, "")

  private val _inputCafeNameState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME_STATE, EditTextState.Idle)
  val inputCafeNameState: StateFlow<EditTextState> = _inputCafeNameState.asStateFlow()

  private val _inputCafeAddressState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_CAFE_ADDRESS_STATE, EditTextState.Idle)
  val inputCafeAddressState: StateFlow<EditTextState> = _inputCafeAddressState.asStateFlow()

  fun handleCafeNameValidation(cafeName: String) {
    when {
      cafeName.isEmpty() -> {
        if (inputCafeNameState.value != EditTextState.Idle) {
          _inputCafeNameState.value = EditTextState.Error()
        }
      }
      else -> {
        _inputCafeName.value = cafeName
        _inputCafeNameState.value = EditTextState.Success
      }
    }
  }

  fun handleCafeAddressValidation(cafeAddress: String) {
    when {
      cafeAddress.isEmpty() -> {
        if (inputCafeAddressState.value != EditTextState.Idle) {
          _inputCafeAddressState.value = EditTextState.Error()
        }
      }
      else -> {
        _inputCafeAddress.value = cafeAddress
        _inputCafeAddressState.value = EditTextState.Success
      }
    }
  }

  fun setCafeImages(imageItems: List<CafeImageItem>) {
    _cafeImages.value = imageItems
  }

  fun deleteCafeImage(position: Int) {
    val updatedList = _cafeImages.value.toMutableList().apply {
      removeAt(position)
    }
    _cafeImages.value = updatedList
  }

  val enableRegisterCafe: StateFlow<Boolean> = combine(
    cafeImages,
    inputCafeNameState,
    inputCafeAddressState,
  ) { images, name, address ->
    images.isNotEmpty() && name.Success && address.Success
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

  companion object {
    private const val KEY_CAFE_IMAGE_URL_LIST = "cafe_image_url_list"
    private const val KEY_CAFE_NAME = "cafe_name"
    private const val KEY_CAFE_NAME_STATE = "cafe_name_state"
    private const val KEY_CAFE_ADDRESS = "cafe_address"
    private const val KEY_CAFE_ADDRESS_STATE = "cafe_address_state"
  }
}
