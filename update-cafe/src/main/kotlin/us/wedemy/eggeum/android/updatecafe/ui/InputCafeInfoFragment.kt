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
  private val guideMessage = "정보를 입력해주세요!" // resources.getString(R.string.guide_message) // 에러발생

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    /**
     * Intent에서 placeId 가져오기
     */
    viewModel.getPlaceBody(1)
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
        viewModel.placeBody.info = infoEntity

        viewModel.upsertPlaceBody()
      }
    }
  }
  private fun stringToBoolean(str: String): Boolean? {
    return if (str == "") null
    else str == "O"
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
        viewModel.cafeInfo.collect { cafeInfo ->
          binding.apply {
            tietInputCafeArea.hint = stringOrElseGuideMessage(cafeInfo.areaSize)
            tietInputCafeMeetingRoom.hint = intToStringOrElseGuideMessage(cafeInfo.meetingRoomCount)
            tietInputCafeMultiSeat.hint = intToStringOrElseGuideMessage(cafeInfo.multiSeatCount)
            tietInputCafeSingleSeat.hint = intToStringOrElseGuideMessage(cafeInfo.singleSeatCount)
            tietInputCafeBusinessHours.hint = cafeInfo.businessHours
              ?.joinToString(" ~ ", "", "", -1) ?: guideMessage
            tietInputParking.hint = stringOrElseGuideMessage(cafeInfo.parking)
            tietInputExistsSmokingArea.hint = boolToStringOrElseGuideMessage(cafeInfo.existsSmokingArea)
            tietInputExistsWifi.hint = boolToStringOrElseGuideMessage(cafeInfo.existsWifi)
            tietInputRestRoom.hint = stringOrElseGuideMessage(cafeInfo.restRoom)
            tietInputMobileCharging.hint = stringOrElseGuideMessage(cafeInfo.mobileCharging)
            tietInputInstagramUri.hint = stringOrElseGuideMessage(cafeInfo.instagramUri)
            tietInputWebsiteUri.hint = stringOrElseGuideMessage(cafeInfo.websiteUri)
            tietInputBlogUri.hint = stringOrElseGuideMessage(cafeInfo.blogUri)
            tietInputPhone.hint = stringOrElseGuideMessage(cafeInfo.phone)

            cafeArea = stringOrElseDefault(cafeInfo.areaSize)
            cafeMeetingRoom = intToStringOrElseDefault(cafeInfo.meetingRoomCount)
            cafeMultiSeat = intToStringOrElseDefault(cafeInfo.multiSeatCount)
            cafeSingleSeat = intToStringOrElseDefault(cafeInfo.singleSeatCount)
            cafeBusinessHours = cafeInfo.businessHours
              ?.joinToString(" ~ ", "", "", -1) ?: ""
            cafeParking = stringOrElseDefault(cafeInfo.parking)
            cafeExistsSmokingArea = boolToStringOrElseDefault(cafeInfo.existsSmokingArea)
            cafeExistsWifi = boolToStringOrElseDefault(cafeInfo.existsWifi)
            cafeRestRoom = stringOrElseDefault(cafeInfo.restRoom)
            cafeMobileCharging = stringOrElseDefault(cafeInfo.mobileCharging)
            cafeWebsiteUri = stringOrElseDefault(cafeInfo.websiteUri)
            cafeInstagramUri = stringOrElseDefault(cafeInfo.instagramUri)
            cafeBlogUri = stringOrElseDefault(cafeInfo.blogUri)
            cafePhone = stringOrElseDefault(cafeInfo.phone)
          }
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

  private fun stringOrElseDefault(str: String?): String {
    return str ?: ""
  }

  private fun intToStringOrElseDefault(int: Int?): String {
    return int?.toString() ?: ""
  }

  private fun boolToStringOrElseDefault(bool: Boolean?): String {
    return if (bool == null) ""
    else if (bool) "O"
    else "X"
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
