/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("unused", "UnusedPrivateProperty")

package us.wedemy.eggeum.android.updatecafe.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceBody
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

  lateinit var upsertPlaceBody: UpsertPlaceBody

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initListener()
    initObserver()
  }

  private fun initListener() {
    with(binding) {
      tbSelectInputCafeInfo.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }
      tietInputCafeArea.doAfterTextChanged {
        Log.d("jaehong", "면적: $it")
        cafeArea = it.toString()
      }
      tietInputCafeMeetingRoom.doAfterTextChanged {
        Log.d("jaehong", "회의실: $it")
        cafeMeetingRoom = it.toString()
      }
      tietInputCafeMultiSeat.doAfterTextChanged {
        Log.d("jaehong", "다인석: $it")
        cafeMultiSeat = it.toString()
      }
      tietInputCafeSingleSeat.doAfterTextChanged {
        Log.d("jaehong", "1인석: $it")
        cafeSingleSeat = it.toString()
      }
      tietInputCafeBusinessHours.doAfterTextChanged {
        Log.d("jaehong", "영업시간: $it")
        cafeBusinessHours = it.toString()
      }
      btnNext.setOnClickListener {
        upsertPlaceBody.updateProposal(
          cafeArea = cafeArea,
          cafeMeetingRoom = cafeMeetingRoom,
          cafeMultiSeat = cafeMultiSeat,
          cafeSingleSeat = cafeSingleSeat,
          cafeBusinessHours = cafeBusinessHours.split(" ~ "),
        )
        /**
         * !!!!!!!!!!!!!!!!
         * Suspend function 'proposeCafeInfo'
         * should be called only from a coroutine or another suspend function
         */
        // viewModel.proposeCafeInfo(upsertPlaceBody)
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.getPlaceBody(1) // 임의 값
        viewModel.cafeInfo.collect { cafeInfo ->
          upsertPlaceBody = cafeInfo
          with(binding) {
            tietInputCafeArea.hint = cafeInfo.info.areaSize.toString()
            tietInputCafeMeetingRoom.hint = cafeInfo.info.meetingRoomCount.toString()
            tietInputCafeMultiSeat.hint = cafeInfo.info.multiSeatCount.toString()
            tietInputCafeSingleSeat.hint = cafeInfo.info.singleSeatCount.toString()
            tietInputCafeBusinessHours.hint = cafeInfo.info.businessHours
              .joinToString(" ~ ", "", "", -1)
          }
        }
      }
    }
  }
}
