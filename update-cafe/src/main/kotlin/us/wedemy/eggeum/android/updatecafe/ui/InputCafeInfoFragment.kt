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
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.common.extension.textChangesAsFlow
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
    initDataObserver()
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
      btnNext.setOnClickListener {
        // TODO: 맨처음에 읽을 때, 데이터 getMutableStateFlow 에 저장
        // TODO: editCafeInfo할 때, getMutableStateFlow에서 placeBody update
        viewModel.editCafeInfo()
        viewModel.updatePlaceBodyUseCase()
      }
    }
  }

  private fun countObserver(cafeInfo: CafeInfoItem) {
    binding.apply {
      cafeInfo.meetingRoomCount?.let { tietInputCafeMeetingRoom.setText(it.toString()) }
      cafeInfo.multiSeatCount?. let { tietInputCafeMultiSeat.setText(it.toString()) }
      cafeInfo.singleSeatCount?. let { tietInputCafeSingleSeat.setText(it.toString()) }
    }
  }

  private fun urlObserver(cafeInfo: CafeInfoItem) {
    binding.apply {
      cafeInfo.instagramUri?.let { tietInputInstagramUri.setText(it) }
      cafeInfo.websiteUri?.let { tietInputWebsiteUri.setText(it) }
      cafeInfo.blogUri?.let { tietInputBlogUri.setText(it) }
    }
  }

  private fun initDataObserver() {
    repeatOnStarted {
      collectTextChanges(this, binding.tietInputCafeArea) { cafeAreaSize ->
        viewModel.setCafeAreaSize(cafeAreaSize)
      }
      collectTextChanges(this, binding.tietInputCafeMeetingRoom) { cafeMeetingRoom ->
        viewModel.setCafeMeetingRoom(cafeMeetingRoom)
      }
      collectTextChanges(this, binding.tietInputCafeMultiSeat) { cafeMultiSeat ->
        viewModel.setCafeMultiSeat(cafeMultiSeat)
      }
      collectTextChanges(this, binding.tietInputCafeSingleSeat) { cafeSingleSeat ->
        viewModel.setCafeSingleSeat(cafeSingleSeat)
      }
      collectTextChanges(this, binding.tietInputCafeBusinessHours) { cafeBusinessHours ->
        viewModel.setCafeBusinessHours(cafeBusinessHours)
      }
      collectTextChanges(this, binding.tietInputParking) { cafeParking ->
        viewModel.setCafeParking(cafeParking)
      }
      collectTextChanges(this, binding.tietInputExistsSmokingArea) { cafeSmoking ->
        viewModel.setCafeSmoking(cafeSmoking)
      }
      collectTextChanges(this, binding.tietInputExistsWifi) { cafeWifi ->
        viewModel.setCafeWifi(cafeWifi)
      }
      collectTextChanges(this, binding.tietInputExistsOutlet) { cafeOutlet ->
        viewModel.setCafeOutlet(cafeOutlet)
      }
      collectTextChanges(this, binding.tietInputRestRoom) { cafeRestRoom ->
        viewModel.setCafeRestRoom(cafeRestRoom)
      }
      collectTextChanges(this, binding.tietInputMobileCharging) { cafeMobileCharging ->
        viewModel.setCafeMobileCharging(cafeMobileCharging)
      }
      collectTextChanges(this, binding.tietInputInstagramUri) { cafeInstagramUrl ->
        viewModel.setCafeInstagramUrl(cafeInstagramUrl)
      }
      collectTextChanges(this, binding.tietInputWebsiteUri) { cafeWebsiteUrl ->
        viewModel.setCafeWebsiteUrl(cafeWebsiteUrl)
      }
      collectTextChanges(this, binding.tietInputBlogUri) { cafeBlogUrl ->
        viewModel.setCafeBlogUrl(cafeBlogUrl)
      }
      collectTextChanges(this, binding.tietInputPhone) { cafePhone ->
        viewModel.setCafePhone(cafePhone)
      }
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

  private fun collectTextChanges(scope: CoroutineScope, editText: EditText, onTextChange: (String) -> Unit) {
    scope.launch {
      editText.textChangesAsFlow()
        .collect { text ->
          onTextChange(text.toString().trim())
        }
    }
  }

  private fun boolToString(bool: Boolean): String {
    return if (bool) "O"
    else "X"
  }
}
