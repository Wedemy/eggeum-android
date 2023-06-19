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

class RegisterCafeViewModel(
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _isPictureSelected = savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME, false)
  val isPictureSelected: StateFlow<Boolean> = _isPictureSelected.asStateFlow()

  private val _inputCafeName = savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME, "")
  val inputCafeName: StateFlow<String> = _inputCafeName.asStateFlow()

  private val _inputCafeAddress = savedStateHandle.getMutableStateFlow(KEY_CAFE_ADDRESS, "")
  val inputCafeAddress: StateFlow<String> = _inputCafeAddress.asStateFlow()

  private val _inputCafeArea = savedStateHandle.getMutableStateFlow(KEY_CAFE_AREA, "")
  val inputCafeArea: StateFlow<String> = _inputCafeArea.asStateFlow()

  private val _inputCafeNameState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME_STATE, EditTextState.Idle)
  val inputCafeNameState: StateFlow<EditTextState> = _inputCafeNameState.asStateFlow()

  private val _inputCafeAddressState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_CAFE_ADDRESS_STATE, EditTextState.Idle)
  val inputCafeAddressState: StateFlow<EditTextState> = _inputCafeAddressState.asStateFlow()

  fun handleCafeNameValidation(cafeName: String) {
    when {
      cafeName.isEmpty() -> {
        if (_inputCafeNameState.value != EditTextState.Idle) {
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
        if (_inputCafeAddressState.value != EditTextState.Idle) {
          _inputCafeAddressState.value = EditTextState.Error()
        }
      }
      else -> {
        _inputCafeAddress.value = cafeAddress
        _inputCafeAddressState.value = EditTextState.Success
      }
    }
  }

//  val enableRegisterCafe: StateFlow<Boolean> = combine(
//    isPictureSelected,
//    inputCafeName,
//    inputCafeAddress,
//  ) { selected, name, address ->
//    selected && name.isNotEmpty() && address.isNotEmpty()
//  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

  val enableRegisterCafe: StateFlow<Boolean> = combine(
    inputCafeNameState,
    inputCafeAddressState,
  ) { name, address ->
    name == EditTextState.Success && address == EditTextState.Success
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

  companion object {
    private const val KEY_CAFE_PICTURE = "cafe_picture"
    private const val KEY_CAFE_NAME = "cafe_name"
    private const val KEY_CAFE_NAME_STATE = "cafe_name_state"
    private const val KEY_CAFE_ADDRESS = "cafe_address"
    private const val KEY_CAFE_ADDRESS_STATE = "cafe_address_state"
    private const val KEY_CAFE_AREA = "cafe_area"
  }
}