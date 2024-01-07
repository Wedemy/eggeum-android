/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("unused")

package us.wedemy.eggeum.android.updatecafe.viewmodel

import androidx.lifecycle.SavedStateHandle
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
import us.wedemy.eggeum.android.common.model.CafeDetailModel
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.domain.model.place.ProductEntity
import us.wedemy.eggeum.android.domain.usecase.GetPlaceUseCase
import us.wedemy.eggeum.android.domain.usecase.UpsertPlaceUseCase
import us.wedemy.eggeum.android.updatecafe.ui.item.CafeInfoItem
import us.wedemy.eggeum.android.updatecafe.ui.item.CafeMenuItem

@HiltViewModel
class ProposeCafeInfoViewModel @Inject constructor(
  private val getPlaceUseCase: GetPlaceUseCase,
  private val upsertPlaceUseCase: UpsertPlaceUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  /*
   * TODO: import us.wedemy.eggeum.android.updatecafe.model.CafeDetailModel
   */
  private val selectedCafeInfo: CafeDetailModel = requireNotNull(savedStateHandle[CAFE_DETAIL_INFO]) {
    "cafeDetailInfo is required."
  }

  private val _navigateToUpsertEvent = MutableSharedFlow<Boolean>()
  val navigateToUpsertEvent = _navigateToUpsertEvent.asSharedFlow()

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()

  private val _getInitCall = MutableSharedFlow<Unit>(1)
  val getInitCall = _getInitCall.asSharedFlow()

  private val _cafeMenuItemMap = MutableStateFlow(emptyMap<String, CafeMenuItem>())
  private val cafeMenuItemMap = _cafeMenuItemMap.asStateFlow()

  fun setCafeMenuItemMap(cafeMenuItemMap: MutableMap<String, CafeMenuItem>) {
    _cafeMenuItemMap.value = cafeMenuItemMap
  }

  lateinit var placeBody: PlaceEntity

  private val _cafeMenuList = MutableStateFlow(emptyList<CafeMenuItem>())
  val cafeMenuList = _cafeMenuList.asStateFlow()

  private val _cafeInfo = MutableStateFlow(CafeInfoItem())
  val cafeInfo = _cafeInfo.asStateFlow()

  init {
    /*
     * TODO:
     *  java.lang.ClassCastException: us.wedemy.eggeum.android.main.model.CafeDetailModel cannot be cast to us.wedemy.eggeum.android.updatecafe.model.CafeDetailModel
     */
    getCafeMenuListAndInfo(selectedCafeInfo.id)
  }

  private fun getCafeMenuListAndInfo(placeId: Int) {
    viewModelScope.launch {
      val result = getPlaceUseCase(placeId)
      when {
        result.isSuccess && result.getOrNull() != null -> {
          placeBody = result.getOrNull()!!
          launch {
            _cafeInfo.update { cafeInfo ->
              cafeInfo.copy(
                areaSize = placeBody.info?.areaSize,
                blogUri = placeBody.info?.blogUri,
                businessHours = placeBody.info?.businessHours,
                existsSmokingArea = placeBody.info?.existsSmokingArea,
                existsWifi = placeBody.info?.existsWifi,
                instagramUri = placeBody.info?.instagramUri,
                meetingRoomCount = placeBody.info?.meetingRoomCount,
                mobileCharging = placeBody.info?.mobileCharging,
                multiSeatCount = placeBody.info?.multiSeatCount,
                parking = placeBody.info?.parking,
                phone = placeBody.info?.phone,
                restRoom = placeBody.info?.restRoom,
                singleSeatCount = placeBody.info?.singleSeatCount,
                websiteUri = placeBody.info?.websiteUri,
              )
            }
          }
          launch {
            placeBody.menu?.products?.let {
              val cafeMenuItemList: MutableList<CafeMenuItem> = initializeCafeMenuItem(products = it)
              updateCafeMenuList(cafeMenuItemList = cafeMenuItemList)
            }
          }
          _getInitCall.emit(Unit)
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

  fun updateCafeMenuList(cafeMenuItemList: MutableList<CafeMenuItem>) {
    _cafeMenuList.value = cafeMenuItemList
  }

  fun editCafeMenuItem() {
    val before = cafeMenuItemMap.value["before"]
    val after = cafeMenuItemMap.value["after"]
    placeBody.menu?.products.let { productEntities ->
      productEntities?.forEach { product ->
        if (product.name == before?.name && product.price == before.price) {
          product.name = after?.name!!
          product.price = after.price
        }
      }
    }

    placeBody.menu?.products?.let {
      val cafeMenuItemList: MutableList<CafeMenuItem> = initializeCafeMenuItem(products = it)
      updateCafeMenuList(cafeMenuItemList = cafeMenuItemList)
    }
  }

  fun updatePlaceBodyUseCase() {
    viewModelScope.launch {
      val result = upsertPlaceUseCase(placeBody.toUpsertPlaceEntity())
      when {
        result.isSuccess -> {
          _navigateToUpsertEvent.emit(true)
        }
        result.isFailure -> {
          val exception = result.exceptionOrNull()
          Timber.d(exception)
          _showToastEvent.emit(exception?.message ?: "Unknown Error Occured")
        }
      }
    }
  }

  // TODO: 에뮬레이터와 달리 실기기로 테스트 시, 해당 state value를 초기화 안해도 마지막 완료 화면으로 넘어가지 않음. (확인 필요)
  fun initializeUpdatePlaceBodySuccess() {
    viewModelScope.launch {
      _navigateToUpsertEvent.emit(false)
    }
  }

  private companion object {
    private const val CAFE_DETAIL_INFO = "cafe_detail_info"
  }
}
