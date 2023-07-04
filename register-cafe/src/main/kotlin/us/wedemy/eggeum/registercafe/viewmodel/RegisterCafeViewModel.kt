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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import us.wedemy.eggeum.common.util.EditTextState
import us.wedemy.eggeum.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.common.util.TextInputError
import us.wedemy.eggeum.common.util.getMutableStateFlow
import us.wedemy.eggeum.common.util.isSuccess
import us.wedemy.eggeum.registercafe.item.CafeImageItem

@HiltViewModel
class RegisterCafeViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
  private val _cafeImages = savedStateHandle.getMutableStateFlow(KEY_CAFE_IMAGE_URL_LIST, emptyList<CafeImageItem>())
  val cafeImages = _cafeImages.asStateFlow()

  private val _cafeName = savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME, "")

  private val _cafeNameState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME_STATE, EditTextState.Idle)
  val cafeNameState = _cafeNameState.asStateFlow()

  private val _cafeAddress = savedStateHandle.getMutableStateFlow(KEY_CAFE_ADDRESS, "")

  private val _cafeAddressState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_CAFE_ADDRESS_STATE, EditTextState.Idle)
  val cafeAddressState = _cafeAddressState.asStateFlow()

  fun handleCafeNameValidation(cafeName: String) {
    _cafeName.value = cafeName
    when {
      _cafeName.value.isEmpty() -> {
        _cafeNameState.value = EditTextState.Error(TextInputError.EMPTY)
      }
      else -> {
        _cafeNameState.value = EditTextState.Success
      }
    }
  }

  fun handleCafeAddressValidation(cafeAddress: String) {
    _cafeAddress.value = cafeAddress
    when {
      _cafeAddress.value.isEmpty() -> {
        _cafeAddressState.value = EditTextState.Error(TextInputError.EMPTY)
      }
      else -> {
        _cafeAddressState.value = EditTextState.Success
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

  val enableRegisterCafe =
    combine(
      cafeImages,
      cafeNameState,
      cafeAddressState,
    ) { images, name, address ->
      images.isNotEmpty() && name.isSuccess && address.isSuccess
    }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false,
      )

  private companion object {
    private const val KEY_CAFE_IMAGE_URL_LIST = "cafe_image_url_list"
    private const val KEY_CAFE_NAME = "cafe_name"
    private const val KEY_CAFE_NAME_STATE = "cafe_name_state"
    private const val KEY_CAFE_ADDRESS = "cafe_address"
    private const val KEY_CAFE_ADDRESS_STATE = "cafe_address_state"
  }
}
