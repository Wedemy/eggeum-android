/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("unused", "UnusedPrivateProperty")

package us.wedemy.eggeum.android.updatecafe.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.domain.model.place.InfoEntity
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceEntity
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentInputCafeInfoBinding
import us.wedemy.eggeum.android.updatecafe.viewmodel.InputCafeInfoViewModel

@AndroidEntryPoint
class InputCafeInfoFragment : BaseFragment<FragmentInputCafeInfoBinding>() {
  override fun getViewBinding() = FragmentInputCafeInfoBinding.inflate(layoutInflater)

  private val viewModel by viewModels<InputCafeInfoViewModel>()

  private var cafeArea = ""
  private var cafeMeetingRoom = ""
  private var cafeMultiSeat = ""
  private var cafeSingleSeat = ""
  private var cafeBusinessHours = ""
  private var cafeParking = ""
  private var cafeExistsSmokingArea = ""
  private var cafeExistsWifi = ""
  private var cafeRestRoom = ""
  private var cafeMobileCharging = ""
  private var cafeInstagramUri = ""
  private var cafeWebsiteUri = ""
  private var cafeBlogUri = ""
  private var cafePhone = ""

  private var cafeName: String = ""
  private val guideMessage = "정보를 입력해주세요!"

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    repeatOnStarted {
      launch {
        /**
         * Intent에서 placeId 가져오기
         */
        viewModel.getPlaceBody(1)
      }
    }
  }

  private fun initListener() {
    with(binding) {
      tbSelectInputCafeInfo.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }
      /**
       * 입력란 별, 검증
       */
      tietInputCafeArea.doAfterTextChanged {
        cafeArea = it.toString()
      }
      tietInputCafeMeetingRoom.doAfterTextChanged {
        cafeMeetingRoom = it.toString()
      }
      tietInputCafeMultiSeat.doAfterTextChanged {
        cafeMultiSeat = it.toString()
      }
      tietInputCafeSingleSeat.doAfterTextChanged {
        cafeSingleSeat = it.toString()
      }
      tietInputCafeBusinessHours.doAfterTextChanged {
        cafeBusinessHours = it.toString()
      }
      tietInputParking.doAfterTextChanged {
        cafeParking = it.toString()
      }
      tietInputExistsSmokingArea.doAfterTextChanged {
        cafeExistsSmokingArea = it.toString()
      }
      tietInputExistsWifi.doAfterTextChanged {
        cafeExistsWifi = it.toString()
      }
      tietInputRestRoom.doAfterTextChanged {
        cafeRestRoom = it.toString()
      }
      tietInputMobileCharging.doAfterTextChanged {
        cafeMobileCharging = it.toString()
      }
      tietInputInstagramUri.doAfterTextChanged {
        cafeInstagramUri = it.toString()
      }
      tietInputWebsiteUri.doAfterTextChanged {
        cafeWebsiteUri = it.toString()
      }
      tietInputBlogUri.doAfterTextChanged {
        cafeBlogUri = it.toString()
      }
      tietInputPhone.doAfterTextChanged {
        cafePhone = it.toString()
      }
      btnNext.setOnClickListener {
        val infoEntity = InfoEntity.of(
          areaSize = stringOrElseNull(cafeArea),
          blogUri = stringOrElseNull(cafeBlogUri),
          businessHours = if (cafeBusinessHours != "") cafeBusinessHours.split(" ~ ") else null,
          existsSmokingArea = stringToBoolean(cafeExistsSmokingArea),
          existsWifi = stringToBoolean(cafeExistsWifi),
          instagramUri = stringOrElseNull(cafeInstagramUri),
          meetingRoomCount = stringToIntOrElseNull(cafeMeetingRoom),
          mobileCharging = stringOrElseNull(cafeMobileCharging),
          multiSeatCount = stringToIntOrElseNull(cafeMultiSeat),
          parking = stringOrElseNull(cafeParking),
          phone = stringOrElseNull(cafePhone),
          restRoom = stringOrElseNull(cafeRestRoom),
          singleSeatCount = stringToIntOrElseNull(cafeSingleSeat),
          websiteUri = stringOrElseNull(cafeWebsiteUri),
        )

        val upsertPlaceEntity = UpsertPlaceEntity.of(
          info = infoEntity,
          placeId = 1,
          name = if (cafeName != "") cafeName else null,
        )

        viewModel.upsertPlaceBody(upsertPlaceEntity = upsertPlaceEntity)
      }
    }
  }
  private fun stringToBoolean(str: String): Boolean {
    return str == "O"
  }

  private fun stringOrElseNull(str: String): String? {
    return if (str != "") str
    else null
  }

  private fun stringToIntOrElseNull(str: String): Int? {
    return if (str != "") str.toInt()
    else null
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.uiState.collect { uiState ->
          with(binding) {
            tietInputCafeArea.hint = stringOrElseGuideMessage(uiState.areaSize)
            tietInputCafeMeetingRoom.hint = intToStringOrElseGuideMessage(uiState.meetingRoomCount)
            tietInputCafeMultiSeat.hint = intToStringOrElseGuideMessage(uiState.multiSeatCount)
            tietInputCafeSingleSeat.hint = intToStringOrElseGuideMessage(uiState.singleSeatCount)
            tietInputCafeBusinessHours.hint = uiState.businessHours
              ?.joinToString(" ~ ", "", "", -1) ?: guideMessage
            tietInputParking.hint = stringOrElseGuideMessage(uiState.parking)
            tietInputExistsSmokingArea.hint = boolToStringOrElseGuideMessage(uiState.existsSmokingArea)
            tietInputExistsWifi.hint = boolToStringOrElseGuideMessage(uiState.existsWifi)
            tietInputRestRoom.hint = stringOrElseGuideMessage(uiState.restRoom)
            tietInputMobileCharging.hint = stringOrElseGuideMessage(uiState.mobileCharging)
            tietInputInstagramUri.hint = stringOrElseGuideMessage(uiState.instagramUri)
            tietInputWebsiteUri.hint = stringOrElseGuideMessage(uiState.websiteUri)
            tietInputBlogUri.hint = stringOrElseGuideMessage(uiState.blogUri)
            tietInputPhone.hint = stringOrElseGuideMessage(uiState.phone)
          }
        }
      }
      launch {
        viewModel.placeName.collect {
          cafeName = it
        }
      }
      launch {
        binding.apply {
          viewModel.navigateToUpsertEvent.collect {
            if (it) {
              val action = InputCafeInfoFragmentDirections.actionFragmentInputCafeInfoToFragmentUpdateMenuComplete()
              findNavController().safeNavigate(action)
            }
          }
        }
      }
      launch {
        viewModel.showToastEvent.collect { message ->
          Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  private fun stringOrElseGuideMessage(str: String?): String {
    return str ?: guideMessage
  }

  private fun intToStringOrElseGuideMessage(int: Int?): String {
    return int?.toString() ?: guideMessage
  }

  private fun boolToStringOrElseGuideMessage(bool: Boolean?): String {
    return if (bool == null) guideMessage
    else {
      if (bool) "O"
      else "X"
    }
  }
}
