/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.register_cafe.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import us.wedemy.eggeum.common.util.EditTextState
import us.wedemy.eggeum.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.common.util.getMutableStateFlow
import us.wedemy.eggeum.register_cafe.item.CafeImageItem

class RegisterCafeViewModel(
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  // TODO 카페 사진 추가를 1장 이상 해야 버튼이 활성화
  private val _isPictureSelected = savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME, false)
  val isPictureSelected: StateFlow<Boolean> = _isPictureSelected.asStateFlow()

  private val _cafeImageList = savedStateHandle.getMutableStateFlow(KEY_CAFE_IMAGE_URL_LIST, emptyList<CafeImageItem>())
  val cafeImageList: StateFlow<List<CafeImageItem>> = _cafeImageList.asStateFlow()

  private val _inputCafeName = savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME, "")

  private val _inputCafeAddress = savedStateHandle.getMutableStateFlow(KEY_CAFE_ADDRESS, "")

  private val _inputCafeArea = savedStateHandle.getMutableStateFlow(KEY_CAFE_AREA, "")

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

  fun setCafeImageList(imageItems: List<CafeImageItem>) {
    _cafeImageList.value = imageItems
  }

  fun deleteCafeImage(position: Int) {
    val updatedList = _cafeImageList.value.toMutableList().apply {
      removeAt(position)
    }
    _cafeImageList.value = updatedList
  }

//  val enableRegisterCafe: StateFlow<Boolean> = combine(
//    isPictureSelected,
//    inputCafeNameState,
//    inputCafeAddressState,
//  ) { selected, name, address ->
//    selected && name.Success && address.Success
//  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

  val enableRegisterCafe: StateFlow<Boolean> = combine(
    inputCafeNameState,
    inputCafeAddressState,
  ) { name, address ->
    name == EditTextState.Success && address == EditTextState.Success
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

  companion object {
    private const val KEY_CAFE_PICTURE = "cafe_picture"
    private const val KEY_CAFE_IMAGE_URL_LIST = "cafe_image_url_list"
    private const val KEY_CAFE_NAME = "cafe_name"
    private const val KEY_CAFE_NAME_STATE = "cafe_name_state"
    private const val KEY_CAFE_ADDRESS = "cafe_address"
    private const val KEY_CAFE_ADDRESS_STATE = "cafe_address_state"
    private const val KEY_CAFE_AREA = "cafe_area"
  }
}