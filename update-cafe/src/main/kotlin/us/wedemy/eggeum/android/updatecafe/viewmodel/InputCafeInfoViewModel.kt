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
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceBody
import us.wedemy.eggeum.android.domain.usecase.GetPlaceBodyUsecase
import us.wedemy.eggeum.android.domain.usecase.ProposeCafeInfoUsecase

// data class ProposalList(
//   val areaSize: String = "",
//   val meetingRoomCount: Int = 0,
//   val multiSeatCount: Int = 0,
//   val singleSeatCount: Int = 0,
//   val businessHours: List<String> = emptyList(),
// )

@HiltViewModel
class InputCafeInfoViewModel @Inject constructor(
  private val getPlaceBodyUsecase: GetPlaceBodyUsecase,
  private val proposeCafeInfoUsecase: ProposeCafeInfoUsecase,
) : ViewModel() {
  private val _cafeInfo = MutableStateFlow(UpsertPlaceBody.fixture())
  val cafeInfo = _cafeInfo.asStateFlow()

  private val _showToastEvent = MutableSharedFlow<String>()
  val showToastEvent: SharedFlow<String> = _showToastEvent.asSharedFlow()
  fun getPlaceBody(placeId: Int) {
    viewModelScope.launch {
      val result = getPlaceBodyUsecase.execute(placeId)
      when {
        result.isSuccess && result.getOrNull() != null -> {
          val placeBody = result.getOrNull()!!
          Timber.d("$placeBody")
          _cafeInfo.update { cafeInfo ->
            cafeInfo.copy(
              address1 = placeBody.address1,
              address2 = placeBody.address2,
              image = placeBody.image,
              info = placeBody.info,
              latitude = placeBody.latitude,
              longitude = placeBody.longitude,
              menu = placeBody.menu,
              name = placeBody.name,
              placeId = placeId,
              remarks = "what it is?",
              type = placeBody.type,
            )
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

  suspend fun proposeCafeInfo(
    updatePlaceBody: UpsertPlaceBody,
  ) {
    proposeCafeInfoUsecase.execute(updatePlaceBody)
  }

  private companion object {
    private const val KEY_CAFE_INFO = "key_cafe_info"
  }
}
