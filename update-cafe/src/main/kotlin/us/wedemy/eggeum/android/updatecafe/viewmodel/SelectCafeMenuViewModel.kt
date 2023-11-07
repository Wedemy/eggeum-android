/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("unused")

package us.wedemy.eggeum.android.updatecafe.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.domain.usecase.GetPlaceUseCase
import us.wedemy.eggeum.android.domain.usecase.UpsertlaceBodyUseCase
import us.wedemy.eggeum.android.updatecafe.ui.item.CafeMenuItem

data class UiState(
  val name: String? = null,
  val price: Int? = null,
) {
  fun toMain(): CafeMenuItem {
    return CafeMenuItem(
      name = name!!,
      price = price!!,
    )
  }
}

data class UiStateList(
  val uiStateList: List<UiState>? = emptyList(),
)

@HiltViewModel
class SelectCafeMenuViewModel @Inject constructor(
  private val getPlaceUseCase: GetPlaceUseCase,
  private val upsertlaceBodyUseCase: UpsertlaceBodyUseCase,
) : ViewModel() {

  private val _cafeMenuList = MutableStateFlow(UiStateList())
  val cafeMenuList = _cafeMenuList.asStateFlow()

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()
  fun getCafeMenuList(placeId: Int) {
    viewModelScope.launch {
      val result = getPlaceUseCase(placeId)
      when {
        result.isSuccess && result.getOrNull() != null -> {
          val placeBody = result.getOrNull()!!
          Timber.d("plcaeBody >>> $placeBody")
          placeBody.menu.products?.let {
            val productList = mutableListOf<UiState>()
            it.forEach { product ->
              productList.add(UiState(name = product.name, price = product.price))
            }
            _cafeMenuList.update { uiStates ->
              uiStates.copy(uiStateList = productList)
            }
          }
        }
        result.isSuccess && result.getOrNull() == null -> {
          Timber.e("Request succeeded but data validation failed.")
        }
        result.isFailure -> {
          val exception = result.exceptionOrNull()
          Timber.d(exception)
          _showToastEvent.emit(exception?.message ?: "Unknown Error Occured")
        }
      }
    }
  }
}
