/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentCafeInfoBinding
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel

@AndroidEntryPoint
class CafeInfoFragment : BaseFragment<FragmentCafeInfoBinding>() {
  override fun getViewBinding() = FragmentCafeInfoBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<CafeDetailViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    binding.apply {
      val cafeDetailInfo = viewModel.cafeDetailInfo.value.info
      tvCafeInfoAreaValue.text = cafeDetailInfo.areaSize
      tvCafeInfoMeetingRoomValue.text = cafeDetailInfo.meetingRoomCount.toString()
      tvCafeInfoMultiSeatValue.text = cafeDetailInfo.multiSeatCount.toString()
      tvCafeInfoSingleSeatValue.text = cafeDetailInfo.singleSeatCount.toString()
      // TODO 이거 확장 축소 가능한 요일별 스피너로 구현해야 함
      tvCafeInfoBusinessHoursValue.text = "매일 09:00 - 21:00"
      tvCafeInfoRestRoomValue.text = cafeDetailInfo.restRoom
      tvCafeInfoParkingValue.text = cafeDetailInfo.parking
      if (cafeDetailInfo.existsSmokingArea == true) {
        ivCafeInfoSmokingValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_o_filled_16)
      } else {
        ivCafeInfoSmokingValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_x_colored_16)
      }
      if (cafeDetailInfo.existsWifi == true) {
        ivCafeInfoWifiValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_o_filled_16)
      } else {
      ivCafeInfoWifiValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_x_colored_16)
      }
      // TODO API 에 콘센트 여부 파라미터 누락 -> 추가 요청
      ivCafeInfoOutletValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_o_filled_16)
      tvCafeInfoMobileChargingValue.text = cafeDetailInfo.mobileCharging
      tvCafeInfoPhoneNumber.text = cafeDetailInfo.phone
      // TODO 텍스트를 클릭하면 웹 페이지가 열리도록
      tvCafeInfoSnsValue.text = ""
    }
  }
}
