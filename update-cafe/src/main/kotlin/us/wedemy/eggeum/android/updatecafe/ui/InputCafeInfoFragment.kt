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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentInputCafeInfoBinding
import us.wedemy.eggeum.android.updatecafe.ui.item.CafeInfoItem
import us.wedemy.eggeum.android.updatecafe.viewmodel.ProposeCafeInfoViewModel

@AndroidEntryPoint
class InputCafeInfoFragment : BaseFragment<FragmentInputCafeInfoBinding>() {
  override fun getViewBinding() = FragmentInputCafeInfoBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<ProposeCafeInfoViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//    initView()
    initListener()
    initObserver()
  }

//  private fun initView() {
//
//  }

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
        viewModel.cafeInfo.value.areaSize = it.toString()
      }
      tietInputCafeBusinessHours.doAfterTextChanged {
        viewModel.cafeInfo.value.businessHours = it.toString().replace(" ", "").split(",")
      }
      tietInputParking.doAfterTextChanged {
        viewModel.cafeInfo.value.parking = it.toString()
      }
      tietInputExistsSmokingArea.doAfterTextChanged {
        viewModel.cafeInfo.value.existsSmokingArea = stringToBoolean(it.toString())
      }
      tietInputExistsWifi.doAfterTextChanged {
        viewModel.cafeInfo.value.existsWifi = stringToBoolean(it.toString())
      }
      tietInputExistsOutlet.doAfterTextChanged {
        viewModel.cafeInfo.value.existsOutlet = stringToBoolean(it.toString())
      }
      tietInputRestRoom.doAfterTextChanged {
        viewModel.cafeInfo.value.restRoom = it.toString()
      }
      tietInputMobileCharging.doAfterTextChanged {
        viewModel.cafeInfo.value.mobileCharging = it.toString()
      }

      urlListener()
      countListener()

      tietInputPhone.doAfterTextChanged {
        viewModel.cafeInfo.value.phone = it.toString()
      }
      btnNext.setOnClickListener {
        viewModel.editCafeInfo()
        viewModel.updatePlaceBodyUseCase()
      }
    }
  }
  private fun stringToBoolean(str: String): Boolean? {
    return when (str) {
      "O" -> true
      "X" -> false
      else -> null
    }
  }

  private fun countListener() {
    with(binding) {
      tietInputCafeMeetingRoom.doAfterTextChanged {
        viewModel.cafeInfo.value.meetingRoomCount = it.toString()
      }
      tietInputCafeMultiSeat.doAfterTextChanged {
        viewModel.cafeInfo.value.multiSeatCount = it.toString()
      }
      tietInputCafeSingleSeat.doAfterTextChanged {
        viewModel.cafeInfo.value.singleSeatCount = it.toString()
      }
    }
  }

  private fun urlListener() {
    with(binding) {
      tietInputInstagramUri.doAfterTextChanged {
        viewModel.cafeInfo.value.instagramUri = it.toString()
      }
      tietInputWebsiteUri.doAfterTextChanged {
        viewModel.cafeInfo.value.websiteUri = it.toString()
      }
      tietInputBlogUri.doAfterTextChanged {
        viewModel.cafeInfo.value.blogUri = it.toString()
      }
    }
  }

  private fun countObserver(cafeInfo: CafeInfoItem) {
    binding.apply {
      cafeInfo.meetingRoomCount?.let { tietInputCafeMeetingRoom.setText(it) }
      cafeInfo.multiSeatCount?. let { tietInputCafeMultiSeat.setText(it) }
      cafeInfo.singleSeatCount?. let { tietInputCafeSingleSeat.setText(it) }
    }
  }

  private fun urlObserver(cafeInfo: CafeInfoItem) {
    binding.apply {
      cafeInfo.instagramUri?.let { tietInputInstagramUri.setText(it) }
      cafeInfo.websiteUri?.let { tietInputWebsiteUri.setText(it) }
      cafeInfo.blogUri?.let { tietInputBlogUri.setText(it) }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.cafeInfo.collect { cafeInfo ->
          binding.apply {
            cafeInfo.areaSize?.let { tietInputCafeArea.setText(it) }
            countObserver(cafeInfo = cafeInfo)
            cafeInfo.businessHours?.let { tietInputCafeBusinessHours.setText(it.joinToString(",", "", "", -1)) }
            cafeInfo.parking?.let { tietInputParking.setText(it) }
            cafeInfo.existsSmokingArea?.let { tietInputExistsSmokingArea.setText(boolToString(it)) }
            cafeInfo.existsWifi?.let { tietInputExistsWifi.setText(boolToString(it)) }
            cafeInfo.existsOutlet?.let { tietInputExistsOutlet.setText(boolToString(it)) }
            cafeInfo.restRoom?.let { tietInputRestRoom.setText(it) }
            cafeInfo.mobileCharging?.let { tietInputMobileCharging.setText(it) }
            urlObserver(cafeInfo = cafeInfo)
            cafeInfo.phone?.let { tietInputPhone.setText(it) }
          }
        }
      }

      launch {
        viewModel.navigateToUpsertEvent.collect {
          val action = InputCafeInfoFragmentDirections.actionFragmentInputCafeInfoToFragmentUpdateMenuComplete()
          findNavController().safeNavigate(action)
        }
        // TODO: 에뮬레이터와 달리 실기기로 테스트 시, 해당 state value를 초기화 안해도 마지막 완료 화면으로 넘어가지 않음. (확인 필요)
        // viewModel.initializeUpdatePlaceBodySuccess()
      }

      launch {
        viewModel.showToastEvent.collect { message ->
          Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  private fun boolToString(bool: Boolean): String {
    return if (bool) "O"
    else "X"
  }
}
