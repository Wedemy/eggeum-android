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
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
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

  /*
   * 카페 정보 수정 관련
   */
  private val _cafeAreaSize = savedStateHandle.getMutableStateFlow(KEY_CAFE_AREA_SIZE, "")
  val cafeAreaSize = _cafeAreaSize.asStateFlow()

  private val _cafeMeetingRoom = savedStateHandle.getMutableStateFlow(KEY_CAFE_MEETING_ROOM, 0)
  val cafeMeetingRoom = _cafeMeetingRoom.asStateFlow()

  private val _cafeMultiSeat = savedStateHandle.getMutableStateFlow(KEY_CAFE_MULTI_SEAT, 0)
  val cafeMultiSeat = _cafeMultiSeat.asStateFlow()

  private val _cafeSingleSeat = savedStateHandle.getMutableStateFlow(KEY_CAFE_SINGLE_SEAT, 0)
  val cafeSingleSeat = _cafeSingleSeat.asStateFlow()

  private val _cafeBusinessHours = savedStateHandle.getMutableStateFlow(KEY_CAFE_BUSINESS_HOURS, emptyList<String>())
  val cafeBusinessHours = _cafeBusinessHours.asStateFlow()

  private val _cafeParking = savedStateHandle.getMutableStateFlow(KEY_CAFE_PARKING, "")
  val cafeParking = _cafeParking.asStateFlow()

  private val _cafeSmoking = savedStateHandle.getMutableStateFlow(KEY_CAFE_SMOKING, "")
  val cafeSmoking = _cafeSmoking.asStateFlow()

  private val _cafeWifi = savedStateHandle.getMutableStateFlow(KEY_CAFE_WIFI, "")
  val cafeWifi = _cafeWifi.asStateFlow()

  private val _cafeOutlet = savedStateHandle.getMutableStateFlow(KEY_CAFE_OUTLET, "")
  val cafeOutlet = _cafeOutlet.asStateFlow()

  private val _cafeRestRoom = savedStateHandle.getMutableStateFlow(KEY_CAFE_REST_ROOM, "")
  val cafeRestRoom = _cafeRestRoom.asStateFlow()

  private val _cafeMobileCharging = savedStateHandle.getMutableStateFlow(KEY_CAFE_MOBILE_CHARGING, "")
  val cafeMobileCharging = _cafeMobileCharging.asStateFlow()

  private val _cafeInstagramUrl = savedStateHandle.getMutableStateFlow(KEY_CAFE_INSTAGRAM_URL, "")
  val cafeInstagramUrl = _cafeInstagramUrl.asStateFlow()

  private val _cafeWebsiteUrl = savedStateHandle.getMutableStateFlow(KEY_CAFE_WEBSITE_URL, "")
  val cafeWebsiteUrl = _cafeWebsiteUrl.asStateFlow()

  private val _cafeBlogUrl = savedStateHandle.getMutableStateFlow(KEY_CAFE_BLOG_URL, "")
  val cafeBlogUrl = _cafeBlogUrl.asStateFlow()

  private val _cafePhone = savedStateHandle.getMutableStateFlow(KEY_CAFE_PHONE, "")
  val cafePhone = _cafePhone.asStateFlow()

  fun setCafeAreaSize(cafeAreaSize: String) {
    _cafeAreaSize.value = cafeAreaSize
  }
  fun setCafeMeetingRoom(cafeMeetingRoom: String) {
    if (cafeMeetingRoom.isEmpty()) _cafeMeetingRoom.value = 0
    else _cafeMeetingRoom.value = cafeMeetingRoom.toInt()
  }
  fun setCafeMultiSeat(cafeMultiSeat: String) {
    if (cafeMultiSeat.isEmpty()) _cafeMultiSeat.value = 0
    else _cafeMultiSeat.value = cafeMultiSeat.toInt()
  }
  fun setCafeSingleSeat(cafeSingleSeat: String) {
    if (cafeSingleSeat.isEmpty()) _cafeSingleSeat.value = 0
    else _cafeSingleSeat.value = cafeSingleSeat.toInt()
  }
  fun setCafeBusinessHours(cafeBusinessHours: String) {
    _cafeBusinessHours.value = cafeBusinessHours.replace(" ", "").split(",")
  }
  fun setCafeParking(cafeParking: String) {
    _cafeParking.value = cafeParking
  }
  fun setCafeSmoking(cafeSmoking: String) {
    _cafeSmoking.value = cafeSmoking
  }
  fun setCafeWifi(cafeWifi: String) {
    _cafeWifi.value = cafeWifi
  }
  fun setCafeOutlet(cafeOutlet: String) {
    _cafeOutlet.value = cafeOutlet
  }
  fun setCafeRestRoom(cafeRestRoom: String) {
    _cafeRestRoom.value = cafeRestRoom
  }
  fun setCafeMobileCharging(cafeMobileCharging: String) {
    _cafeMobileCharging.value = cafeMobileCharging
  }
  fun setCafeInstagramUrl(cafeInstagramUrl: String) {
    _cafeInstagramUrl.value = cafeInstagramUrl
  }
  fun setCafeWebsiteUrl(cafeWebsiteUrl: String) {
    _cafeWebsiteUrl.value = cafeWebsiteUrl
  }
  fun setCafeBlogUrl(cafeBlogUrl: String) {
    _cafeBlogUrl.value = cafeBlogUrl
  }
  fun setCafePhone(cafePhone: String) {
    _cafePhone.value = cafePhone
  }

  init {
    getCafeMenuListAndInfo(selectedCafeInfo.id)
  }

  private fun getCafeMenuListAndInfo(placeId: Long) {
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
                existsOutlet = placeBody.info?.existsOutlet,
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

  fun editCafeInfo() {
    viewModelScope.launch {
      _cafeInfo.update { cafeInfo ->
        cafeInfo.copy(
          areaSize = cafeAreaSize.value,
          meetingRoomCount = cafeMeetingRoom.value,
          multiSeatCount = cafeMultiSeat.value,
          singleSeatCount = cafeSingleSeat.value,
          businessHours = cafeBusinessHours.value,
          parking = cafeParking.value,
          existsSmokingArea = cafeSmoking.value.lowercase() == "o",
          existsWifi = cafeWifi.value.lowercase() == "o",
          existsOutlet = cafeOutlet.value.lowercase() == "o",
          restRoom = cafeRestRoom.value,
          mobileCharging = cafeMobileCharging.value,
          instagramUri = cafeInstagramUrl.value,
          websiteUri = cafeWebsiteUrl.value,
          blogUri = cafeBlogUrl.value,
          phone = cafePhone.value,
        )
      }
    }

    placeBody.info = cafeInfo.value.toEntity()
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

    private const val KEY_CAFE_AREA_SIZE = "cafe_area"
    private const val KEY_CAFE_MEETING_ROOM = "cafe_meeting_room"
    private const val KEY_CAFE_MULTI_SEAT = "cafe_multi_seat"
    private const val KEY_CAFE_SINGLE_SEAT = "cafe_single_seat"

    private const val KEY_CAFE_BUSINESS_HOURS = "cafe_business_hours"
    private const val KEY_CAFE_PARKING = "cafe_parking"

    private const val KEY_CAFE_SMOKING = "cafe_smoke"
    private const val KEY_CAFE_WIFI = "cafe_wifi"
    private const val KEY_CAFE_OUTLET = "cafe_outlet"

    private const val KEY_CAFE_REST_ROOM = "cafe_rest_room"
    private const val KEY_CAFE_MOBILE_CHARGING = "cafe_mobile_charging"

    private const val KEY_CAFE_INSTAGRAM_URL = "cafe_instagram_uri"
    private const val KEY_CAFE_WEBSITE_URL = "cafe_website_uri"
    private const val KEY_CAFE_BLOG_URL = "cafe_blog_uri"

    private const val KEY_CAFE_PHONE = "cafe_phone"
  }
}
