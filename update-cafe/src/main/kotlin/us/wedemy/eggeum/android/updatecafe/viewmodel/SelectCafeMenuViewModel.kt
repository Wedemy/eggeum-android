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
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.domain.model.place.ProductEntity
import us.wedemy.eggeum.android.domain.usecase.GetPlaceUseCase
import us.wedemy.eggeum.android.domain.usecase.UpsertlaceBodyUseCase
import us.wedemy.eggeum.android.updatecafe.ui.item.CafeMenuItem

@HiltViewModel
class SelectCafeMenuViewModel @Inject constructor(
  private val getPlaceUseCase: GetPlaceUseCase,
  private val upsertlaceBodyUseCase: UpsertlaceBodyUseCase,
) : ViewModel() {

  private val _cafeMenuList = MutableStateFlow(emptyList<CafeMenuItem>())
  val cafeMenuList = _cafeMenuList.asStateFlow()

  lateinit var placeBody: PlaceEntity

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()

  fun getCafeMenuList(placeId: Int) {
    viewModelScope.launch {
      val result = getPlaceUseCase(placeId)
      when {
        result.isSuccess && result.getOrNull() != null -> {
          placeBody = result.getOrNull()!!
          Timber.d("plcaeBody >>> $placeBody")
          placeBody.menu?.products?.let {
            val productList: MutableList<CafeMenuItem> = initializeCafeMenuItem(products = it)
            updateCafeMenuList(productList = productList)
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

  fun initializeCafeMenuItem(products: List<ProductEntity>): MutableList<CafeMenuItem> {
    val productList = mutableListOf<CafeMenuItem>()
    products.forEach { product ->
      productList.add(CafeMenuItem(name = product.name, price = product.price))
    }
    return productList
  }

  fun updateCafeMenuList(productList: MutableList<CafeMenuItem>) {
    _cafeMenuList.value = productList
  }

  fun updatePlaceBodyUseCase() {
    viewModelScope.launch {
      val result = upsertlaceBodyUseCase(placeBody.toUpsertPlaceEntity())
      when {
        result.isSuccess -> {
          // TODO: 메뉴 수정을 완료했어요 fragment
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
