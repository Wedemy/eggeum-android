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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.common.util.EditTextState
import us.wedemy.eggeum.android.common.util.SaveableMutableStateFlow
import us.wedemy.eggeum.android.common.util.TextInputError
import us.wedemy.eggeum.android.common.util.getMutableStateFlow
import us.wedemy.eggeum.android.common.util.isSuccess
import us.wedemy.eggeum.android.domain.model.FileEntity
import us.wedemy.eggeum.android.domain.model.file.FileResponseEntity
import us.wedemy.eggeum.android.domain.model.place.ImageEntity
import us.wedemy.eggeum.android.domain.model.place.InfoEntity
import us.wedemy.eggeum.android.domain.model.place.MenuEntity
import us.wedemy.eggeum.android.domain.model.place.ProductEntity
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceEntity
import us.wedemy.eggeum.android.domain.usecase.UploadImageFileUseCase
import us.wedemy.eggeum.android.domain.usecase.UpsertPlaceUseCase
import us.wedemy.eggeum.android.registercafe.model.CafeImageModel

@HiltViewModel
class RegisterCafeViewModel @Inject constructor(
  private val upsertPlaceUseCase: UpsertPlaceUseCase,
  private val uploadImageFileUseCase: UploadImageFileUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {
  private val _navigateToRegisterCafeCompleteEvent = MutableSharedFlow<Unit>(replay = 1)
  val navigateToRegisterCafeCompleteEvent: SharedFlow<Unit> = _navigateToRegisterCafeCompleteEvent.asSharedFlow()

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()

  private val _cafeImages = savedStateHandle.getMutableStateFlow(KEY_CAFE_IMAGE_URL_LIST, emptyList<CafeImageModel>())
  val cafeImages = _cafeImages.asStateFlow()

  private val _cafeUploadFiles = MutableStateFlow<List<FileResponseEntity>>(listOf())
  val cafeUploadFiles = _cafeUploadFiles.asStateFlow()

  private val _cafeName = savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME, "")

  private val _cafeNameState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_CAFE_NAME_STATE, EditTextState.Idle)
  val cafeNameState = _cafeNameState.asStateFlow()

  private val _cafeAddress = savedStateHandle.getMutableStateFlow(KEY_CAFE_ADDRESS, "")

  private val _cafeAddressState: SaveableMutableStateFlow<EditTextState> =
    savedStateHandle.getMutableStateFlow(KEY_CAFE_ADDRESS_STATE, EditTextState.Idle)
  val cafeAddressState = _cafeAddressState.asStateFlow()

  private val _cafeArea = savedStateHandle.getMutableStateFlow(KEY_CAFE_AREA, 0)
  val cafeArea = _cafeArea.asStateFlow()

  private val _cafeMeetingRoom = savedStateHandle.getMutableStateFlow(KEY_CAFE_MEETING_ROOM, 0)
  val cafeMeetingRoom = _cafeMeetingRoom.asStateFlow()

  private val _cafeMultiSeat = savedStateHandle.getMutableStateFlow(KEY_CAFE_MULTI_SEAT, 0)
  val cafeMultiSeat = _cafeMultiSeat.asStateFlow()

  private val _cafeSingleSeat = savedStateHandle.getMutableStateFlow(KEY_CAFE_SINGLE_SEAT, 0)
  val cafeSingleSeat = _cafeSingleSeat.asStateFlow()

  private val _cafeBusinessHours = savedStateHandle.getMutableStateFlow(KEY_CAFE_BUSINESS_HOURS, emptyList<String>())
  val cafeBusinessHours = _cafeBusinessHours.asStateFlow()

  private val _cafeRestRoom = savedStateHandle.getMutableStateFlow(KEY_CAFE_REST_ROOM, "")
  val cafeRestRoom = _cafeRestRoom.asStateFlow()

  private val _cafeParking = savedStateHandle.getMutableStateFlow(KEY_CAFE_PARKING, "")
  val cafeParking = _cafeParking.asStateFlow()

  private val _cafeSmokeArea = savedStateHandle.getMutableStateFlow(KEY_CAFE_SMOKE, false)
  val cafeSmokeArea = _cafeSmokeArea.asStateFlow()

  private val _cafeWifi = savedStateHandle.getMutableStateFlow(KEY_CAFE_WIFI, false)
  val cafeWifi = _cafeWifi.asStateFlow()

  private val _cafeOutlet = savedStateHandle.getMutableStateFlow(KEY_CAFE_OUTLET, false)
  val cafeOutlet = _cafeOutlet.asStateFlow()

  private val _cafeMobileCharging = savedStateHandle.getMutableStateFlow(KEY_CAFE_MOBILE_CHARGING, "")
  val cafeMobileCharging = _cafeMobileCharging.asStateFlow()

  private val _cafePhone = savedStateHandle.getMutableStateFlow(KEY_CAFE_PHONE, "")
  val cafePhone = _cafePhone.asStateFlow()

  private val _cafeBlogUri = savedStateHandle.getMutableStateFlow(KEY_CAFE_BLOG_URI, "")
  val cafeBlogUri = _cafeBlogUri.asStateFlow()

  private val _cafeInstagramUri = savedStateHandle.getMutableStateFlow(KEY_CAFE_INSTAGRAM_URI, "")
  val cafeInstagramUri = _cafeInstagramUri.asStateFlow()

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

  fun setCafeImages(imageItems: List<CafeImageModel>) {
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
    if (cafeArea.isEmpty()) _cafeArea.value = 0
    else _cafeArea.value = cafeArea.toInt()
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

  @Suppress("TooGenericExceptionCaught")
  fun registerCafe() {
    val uploadJobs = cafeImages.value.map { image ->
      viewModelScope.async {
        uploadImageFileUseCase(image.url)
      }
    }

    viewModelScope.launch {
      try {
        val results = uploadJobs.awaitAll()
        val isSuccess = results.all { it.isSuccess }

        if (isSuccess) {
          val uploadImageFiles = results.mapNotNull { it.getOrNull() }
            .map { fileResponse ->
              FileEntity(
                uploadFileId = fileResponse.uploadFileId,
                url = fileResponse.url
              )
            }
          val (cafeLatitude, cafeLongitude) = getRandomCoordinate()
          val upsertPlace = UpsertPlaceEntity(
            address1 = _cafeAddress.value,
            address2 = null,
            image = ImageEntity(
              files = uploadImageFiles,
            ),
            info = InfoEntity(
              areaSize = cafeArea.value.toString(),
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
              ),
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
        } else {
          _showToastEvent.emit("파일 업로드를 실패 했습니다")
        }
      } catch (exception: Exception) {
        Timber.e(exception)
        _showToastEvent.emit(exception.message ?: "Unknown Error Occured")
      }
    }
  }

  // 서울의 위도 경도 범위 내에서 랜덤하게 위도와 경도 생성
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

  // 카페 타입 랜덤 지정
  private fun getRandomPlaceType(): String {
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
