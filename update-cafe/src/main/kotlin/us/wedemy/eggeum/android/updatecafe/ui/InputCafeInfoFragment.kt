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
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.domain.model.place.ProposePlaceInfoCommand
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

  lateinit var proposePlaceInfoCommand: ProposePlaceInfoCommand

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initListener()
    initObserver()
  }

  private fun initListener() {
    /**
     * Intent -> placeId 가져오기
     */
    with(binding) {
      tbSelectInputCafeInfo.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }
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
        /**
         * !!!!!!!!!!!!!!!!
         * Suspend function 'proposeCafeInfo'
         * should be called only from a coroutine or another suspend function
         */
          proposePlaceInfoCommand.info.setProposeBody(
            cafeArea = cafeArea,
            cafeMeetingRoom = cafeMeetingRoom,
            cafeMultiSeat = cafeMultiSeat,
            cafeSingleSeat = cafeSingleSeat,
            cafeBusinessHours = cafeBusinessHours,
            cafeParking = cafeParking,
            cafeExistsSmokingArea = cafeExistsSmokingArea,
            cafeExistsWifi = cafeExistsWifi,
            cafeRestRoom = cafeRestRoom,
            cafeMobileCharging = cafeMobileCharging,
            cafeInstagramUri = cafeInstagramUri,
            cafeWebsiteUri = cafeWebsiteUri,
            cafeBlogUri = cafeBlogUri,
            cafePhone = cafePhone,
          )

          //viewModel.proposeCafeInfo(proposePlaceInfoCommand)

      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.getPlaceBody(1) // 임의 값
        viewModel.cafeInfo.collect { cafeInfo ->
          proposePlaceInfoCommand = cafeInfo
          with(binding) {
            tietInputCafeArea.hint = cafeInfo.info.areaSize?.toString()
            tietInputCafeMeetingRoom.hint = cafeInfo.info.meetingRoomCount?.toString()
            tietInputCafeMultiSeat.hint = cafeInfo.info.multiSeatCount?.toString()
            tietInputCafeSingleSeat.hint = cafeInfo.info.singleSeatCount?.toString()
            tietInputCafeBusinessHours.hint = cafeInfo.info.businessHours
              .joinToString(" ~ ", "", "", -1)
            tietInputParking.hint = cafeInfo.info.parking?.toString()
            tietInputExistsSmokingArea.hint = cafeInfo.info.existsSmokingArea?.toString()
            tietInputExistsWifi.hint = cafeInfo.info.existsWifi?.toString()
            tietInputRestRoom.hint = cafeInfo.info.restRoom?.toString()
            tietInputMobileCharging.hint = cafeInfo.info.mobileCharging?.toString()
            tietInputInstagramUri.hint = cafeInfo.info.instagramUri?.toString()
            tietInputWebsiteUri.hint = cafeInfo.info.websiteUri?.toString()
            tietInputBlogUri.hint = cafeInfo.info.blogUri?.toString()
            tietInputPhone.hint = cafeInfo.info.phone?.toString()
          }
        }
      }
    }
  }
}
