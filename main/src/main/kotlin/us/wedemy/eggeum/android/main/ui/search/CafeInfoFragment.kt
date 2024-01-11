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
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.model.InfoModel
import us.wedemy.eggeum.android.main.databinding.FragmentCafeInfoBinding
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel

// TODO 블로그, 인스타, 웹사이트 를 클릭 했을 때, 웹 브라우저로 해당 url 이 실행 되도록
@AndroidEntryPoint
class CafeInfoFragment : BaseFragment<FragmentCafeInfoBinding>() {
  override fun getViewBinding() = FragmentCafeInfoBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<CafeDetailViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initObserver()
  }

  private fun initView() {
    viewModel.cafeDetailInfo.value.info?.let { updateCafeInfo(it) }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.cafeDetailInfo.collect { cafeDetailModel ->
          cafeDetailModel.info?.let { info -> updateCafeInfo(info) }
        }
      }
    }
  }

  private fun updateCafeInfo(cafeDetailInfo: InfoModel) {
    binding.apply {
      tvCafeInfoAreaValue.text = if (cafeDetailInfo.areaSize.isNullOrEmpty()) ""
      else cafeDetailInfo.areaSize + "m²"
      tvCafeInfoMeetingRoomValue.text = (cafeDetailInfo.meetingRoomCount ?: "").toString()
      tvCafeInfoMultiSeatValue.text = (cafeDetailInfo.multiSeatCount ?: "").toString()
      tvCafeInfoSingleSeatValue.text = (cafeDetailInfo.singleSeatCount ?: "").toString()
      // TODO 이거 확장 축소 가능한 요일별 리스트를 구현해야 함 (디자인 필요)
      tvCafeInfoBusinessHoursValue.text = "매일 09:00 - 21:00"
      tvCafeInfoRestRoomValue.text = cafeDetailInfo.restRoom
      tvCafeInfoParkingValue.text = cafeDetailInfo.parking
      if (cafeDetailInfo.existsSmokingArea == true) {
        ivCafeInfoSmokingValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_o_filled_16)
      } else if (cafeDetailInfo.existsSmokingArea == false) {
        ivCafeInfoSmokingValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_x_colored_16)
      }
      if (cafeDetailInfo.existsWifi == true) {
        ivCafeInfoWifiValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_o_filled_16)
      } else if (cafeDetailInfo.existsWifi == false) {
        ivCafeInfoWifiValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_x_colored_16)
      }
      if (cafeDetailInfo.existsOutlet == true) {
        ivCafeInfoOutletValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_o_filled_16)
      } else if (cafeDetailInfo.existsOutlet == false) {
        ivCafeInfoOutletValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_x_colored_16)
      }
      tvCafeInfoMobileChargingValue.text = cafeDetailInfo.mobileCharging
      tvCafeInfoPhoneNumber.text = cafeDetailInfo.phone
      // TODO 텍스트를 클릭하면 웹 페이지가 열리도록
      tvCafeInfoSnsValue.text = ""
    }
  }
}
