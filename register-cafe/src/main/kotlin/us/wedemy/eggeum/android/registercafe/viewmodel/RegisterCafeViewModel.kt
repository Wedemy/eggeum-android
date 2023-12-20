/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.registercafe.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.common.util.EditTextState
import us.wedemy.eggeum.android.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.android.common.util.TextInputError
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.common.util.isSuccess
import us.wedemy.eggeum.android.domain.model.place.ImageEntity
import us.wedemy.eggeum.android.domain.model.place.InfoEntity
import us.wedemy.eggeum.android.domain.model.place.MenuEntity
import us.wedemy.eggeum.android.domain.model.place.ProductEntity
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceEntity
import us.wedemy.eggeum.android.domain.usecase.UpsertPlaceUseCase
import us.wedemy.eggeum.android.registercafe.model.CafeImageItem

@HiltViewModel
class RegisterCafeViewModel @Inject constructor(
  private val upsertPlaceUseCase: UpsertPlaceUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _navigateToRegisterCafeCompleteEvent = MutableSharedFlow<Unit>(replay = 1)
  val navigateToRegisterCafeCompleteEvent: SharedFlow<Unit> = _navigateToRegisterCafeCompleteEvent.asSharedFlow()

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()

  // 카페 사진
  private val _cafeImages = savedStateHandle.getMutableStateFlow(KEY_CAFE_IMAGE_URL_LIST, emptyList<CafeImageItem>())
  val cafeImages = _cafeImages.asStateFlow()

  // 카페 이름
  private val _cafeName = savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME, "")

  private val _cafeNameState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME_STATE, EditTextState.Idle)
  val cafeNameState = _cafeNameState.asStateFlow()

  // 카페 주소
  private val _cafeAddress = savedStateHandle.getMutableStateFlow(KEY_CAFE_ADDRESS, "")

  private val _cafeAddressState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_CAFE_ADDRESS_STATE, EditTextState.Idle)
  val cafeAddressState = _cafeAddressState.asStateFlow()

  // 카페 면적
  private val _cafeArea = savedStateHandle.getMutableStateFlow(KEY_CAFE_AREA, "")
  val cafeArea = _cafeArea.asStateFlow()

  // 회의실
  private val _cafeMeetingRoom = savedStateHandle.getMutableStateFlow(KEY_CAFE_MEETING_ROOM, 0)
  val cafeMeetingRoom = _cafeMeetingRoom.asStateFlow()

  // 다인석
  private val _cafeMultiSeat = savedStateHandle.getMutableStateFlow(KEY_CAFE_MULTI_SEAT, 0)
  val cafeMultiSeat = _cafeMultiSeat.asStateFlow()

  // 1인석
  private val _cafeSingleSeat = savedStateHandle.getMutableStateFlow(KEY_CAFE_SINGLE_SEAT, 0)
  val cafeSingleSeat = _cafeSingleSeat.asStateFlow()

  // TODO 영업 시간
  private val _cafeBusinessHours = savedStateHandle.getMutableStateFlow(KEY_CAFE_BUSINESS_HOURS, emptyList<String>())
  val cafeBusinessHours = _cafeBusinessHours.asStateFlow()

  // 화장실
  private val _cafeRestRoom = savedStateHandle.getMutableStateFlow(KEY_CAFE_REST_ROOM, "")
  val cafeRestRoom = _cafeRestRoom.asStateFlow()

  // 주차
  private val _cafeParking = savedStateHandle.getMutableStateFlow(KEY_CAFE_PARKING, "")
  val cafeParking = _cafeParking.asStateFlow()

  // TODO 흡연구역
  private val _cafeSmokeArea = savedStateHandle.getMutableStateFlow(KEY_CAFE_SMOKE, false)
  val cafeSmokeArea = _cafeSmokeArea.asStateFlow()

  // TODO WIFI
  private val _cafeWifi = savedStateHandle.getMutableStateFlow(KEY_CAFE_WIFI, false)
  val cafeWifi = _cafeWifi.asStateFlow()

  // TODO 콘센트
  private val _cafeOutlet = savedStateHandle.getMutableStateFlow(KEY_CAFE_OUTLET, false)
  val cafeOutlet = _cafeOutlet.asStateFlow()

  // 휴대폰 충전
  private val _cafeMobileCharging = savedStateHandle.getMutableStateFlow(KEY_CAFE_MOBILE_CHARGING, "")
  val cafeMobileCharging = _cafeMobileCharging.asStateFlow()

  // 전화
  private val _cafePhone = savedStateHandle.getMutableStateFlow(KEY_CAFE_PHONE, "")
  val cafePhone = _cafePhone.asStateFlow()

  // 블로그 Uri
  private val _cafeBlogUri = savedStateHandle.getMutableStateFlow(KEY_CAFE_BLOG_URI, "")
  val cafeBlogUri = _cafeBlogUri.asStateFlow()

  // 인스타그램 Uri
  private val _cafeInstagramUri = savedStateHandle.getMutableStateFlow(KEY_CAFE_INSTAGRAM_URI, "")
  val cafeInstagramUri = _cafeInstagramUri.asStateFlow()

  // 웹사이트 Uri
  private val _cafeWebsiteUri = savedStateHandle.getMutableStateFlow(KEY_CAFE_WEBSITE_URI, "")
  val cafeWebsiteUri = _cafeWebsiteUri.asStateFlow()

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

  fun setCafeArea(cafeArea: String) {
    _cafeArea.value = cafeArea
  }

  fun setCafeMeetingRoom(cafeMeetingRoom: String) {
    _cafeMeetingRoom.value = cafeMeetingRoom.toInt()
  }

  fun setCafeMultiSeat(cafeMultiSeat: String) {
    _cafeMultiSeat.value = cafeMultiSeat.toInt()
  }

  fun setCafeSingleSeat(cafeSingleSeat: String) {
    _cafeSingleSeat.value = cafeSingleSeat.toInt()
  }

  fun setCafeBusinessHours(cafeBusinessHours: String) {
    _cafeBusinessHours.value = listOf(cafeBusinessHours)
  }

  fun setCafeRestRoom(cafeRestRoom: String) {
    _cafeRestRoom.value = cafeRestRoom
  }

  fun setCafeParking(cafeParking: String) {
    _cafeParking.value = cafeParking
  }

  fun setCafeSmokeArea(cafeSmokeArea: String) {
    _cafeSmokeArea.value = cafeSmokeArea.lowercase() == "o"
  }

  fun setCafeWifi(cafeWifi: String) {
    _cafeWifi.value = cafeWifi.lowercase() == "o"
  }

  fun setCafeOutlet(cafeOutlet: String) {
    _cafeOutlet.value = cafeOutlet.lowercase() == "o"
  }

  fun setCafeMobileCharging(cafeMobileCharging: String) {
    _cafeMobileCharging.value = cafeMobileCharging
  }

  fun setCafePhone(cafePhone: String) {
    _cafePhone.value = cafePhone
  }

  fun setCafeBlogUri(cafeBlogUri: String) {
    _cafeBlogUri.value = cafeBlogUri
  }

  fun setCafeInstagramUri(cafeInstagramUri: String) {
    _cafeInstagramUri.value = cafeInstagramUri
  }

  fun setCafeWebsiteUri(cafeWebsiteUri: String) {
    _cafeWebsiteUri.value = cafeWebsiteUri
  }

  fun registerCafe() {
    viewModelScope.launch {
      val (cafeLatitude, cafeLongitude) = getRandomCoordinate()
      val upsertPlace = UpsertPlaceEntity(
        address1 = _cafeAddress.value,
        address2 = null,
        image = ImageEntity(listOf()),
        info = InfoEntity(
          areaSize = cafeArea.value,
          blogUri = cafeBlogUri.value,
          businessHours = cafeBusinessHours.value,
          existsSmokingArea = cafeSmokeArea.value,
          existsWifi = cafeWifi.value,
          instagramUri = cafeInstagramUri.value,
          meetingRoomCount = cafeMeetingRoom.value,
          mobileCharging = cafeMobileCharging.value,
          multiSeatCount = cafeMultiSeat.value,
          parking = cafeParking.value,
          phone = cafePhone.value,
          restRoom = cafeRestRoom.value,
          singleSeatCount = cafeSingleSeat.value,
          websiteUri = cafeWebsiteUri.value,
        ),
        latitude = cafeLatitude,
        longitude = cafeLongitude,
        menu = MenuEntity(
          listOf(
            ProductEntity(name = "아메리카노", price = 4000),
            ProductEntity(name = "카페라떼", price = 5000),
          )
        ),
        name = _cafeName.value,
        placeId = null,
        remarks = null,
        type = getRandomPlaceType(),
      )
      val result = upsertPlaceUseCase(upsertPlace)
      when {
        result.isSuccess -> {
          _navigateToRegisterCafeCompleteEvent.emit(Unit)
        }
        result.isFailure -> {
          val exception = result.exceptionOrNull()
          Timber.d(exception)
          _showToastEvent.emit(exception?.message ?: "Unknown Error Occured")
        }
      }
    }
  }

  private fun getRandomCoordinate(): Pair<Double, Double> {
    // 서울의 위도와 경도 범위
    val minLatitude = 37.4
    val maxLatitude = 37.7
    val minLongitude = 126.8
    val maxLongitude = 127.2

    // 랜덤한 위도와 경도 생성
    val latitude = minLatitude + (maxLatitude - minLatitude) * Random.nextDouble()
    val longitude = minLongitude + (maxLongitude - minLongitude) * Random.nextDouble()

    return Pair(latitude, longitude)
  }

  fun getRandomPlaceType(): String {
    val places = listOf("CAFE", "STUDY_CAFE", "STUDY_ROOM")
    return places.random()
  }


  private companion object {
    private const val KEY_CAFE_IMAGE_URL_LIST = "cafe_image_url_list"
    private const val KEY_CAFE_NAME = "cafe_name"
    private const val KEY_CAFE_NAME_STATE = "cafe_name_state"
    private const val KEY_CAFE_ADDRESS = "cafe_address"
    private const val KEY_CAFE_ADDRESS_STATE = "cafe_address_state"
    private const val KEY_CAFE_AREA = "cafe_area"
    private const val KEY_CAFE_MEETING_ROOM = "cafe_meeting_room"
    private const val KEY_CAFE_MULTI_SEAT = "cafe_multi_seat"
    private const val KEY_CAFE_SINGLE_SEAT = "cafe_single_seat"
    private const val KEY_CAFE_BUSINESS_HOURS = "cafe_business_hours"
    private const val KEY_CAFE_REST_ROOM = "cafe_rest_room"
    private const val KEY_CAFE_PARKING = "cafe_parking"
    private const val KEY_CAFE_SMOKE = "cafe_smoke"
    private const val KEY_CAFE_WIFI = "cafe_wifi"
    private const val KEY_CAFE_OUTLET = "cafe_outlet"
    private const val KEY_CAFE_MOBILE_CHARGING = "cafe_mobile_charging"
    private const val KEY_CAFE_PHONE = "cafe_phone"
    private const val KEY_CAFE_BLOG_URI = "cafe_blog_uri"
    private const val KEY_CAFE_INSTAGRAM_URI = "cafe_instagram_uri"
    private const val KEY_CAFE_WEBSITE_URI = "cafe_website_uri"
  }
}
