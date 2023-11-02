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
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceEntity
import us.wedemy.eggeum.android.domain.usecase.GetPlaceUseCase
import us.wedemy.eggeum.android.domain.usecase.UpsertlaceBodyUseCase

data class CafeInfoUiState(
  val areaSize: String? = null,
  val blogUri: String? = null,
  val businessHours: List<String>? = null,
  val existsSmokingArea: Boolean? = null,
  val existsWifi: Boolean? = null,
  val instagramUri: String? = null,
  val meetingRoomCount: Int? = null,
  val mobileCharging: String? = null,
  val multiSeatCount: Int? = null,
  val parking: String? = null,
  val phone: String? = null,
  val restRoom: String? = null,
  val singleSeatCount: Int? = null,
  val websiteUri: String? = null,
)

@HiltViewModel
class InputCafeInfoViewModel @Inject constructor(
  private val getPlaceUseCase: GetPlaceUseCase,
  private val upsertlaceBodyUseCase: UpsertlaceBodyUseCase,
) : ViewModel() {
  private val _uiState = MutableStateFlow(CafeInfoUiState())
  val uiState = _uiState.asStateFlow()

  private val _placeName = MutableStateFlow<String>("")
  val placeName = _placeName.asStateFlow()

  private val _navigateToUpsertEvent = MutableSharedFlow<Boolean>(0)
  val navigateToUpsertEvent: SharedFlow<Boolean> = _navigateToUpsertEvent.asSharedFlow()

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()
  fun getPlaceBody(placeId: Int) {
    viewModelScope.launch {
      val result = getPlaceUseCase(placeId)
      when {
        result.isSuccess && result.getOrNull() != null -> {
          val placeBody = result.getOrNull()!!
          Timber.d("plcaeBody >>> $placeBody")
          _uiState.update { uiState ->
            uiState.copy(
              areaSize = placeBody.info.areaSize,
              blogUri = placeBody.info.blogUri,
              businessHours = placeBody.info.businessHours,
              existsSmokingArea = placeBody.info.existsSmokingArea,
              existsWifi = placeBody.info.existsWifi,
              instagramUri = placeBody.info.instagramUri,
              meetingRoomCount = placeBody.info.meetingRoomCount,
              mobileCharging = placeBody.info.mobileCharging,
              multiSeatCount = placeBody.info.multiSeatCount,
              parking = placeBody.info.parking,
              phone = placeBody.info.phone,
              restRoom = placeBody.info.restRoom,
              singleSeatCount = placeBody.info.singleSeatCount,
              websiteUri = placeBody.info.websiteUri,
            )
          }
          _placeName.emit(placeBody.name)
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

  fun upsertPlaceBody(upsertPlaceEntity: UpsertPlaceEntity) {
    viewModelScope.launch {
      val result = upsertlaceBodyUseCase(upsertPlaceEntity)
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
}
