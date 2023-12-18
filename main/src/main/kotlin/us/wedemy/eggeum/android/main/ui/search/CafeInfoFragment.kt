/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.FragmentCafeInfoBinding
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel

@AndroidEntryPoint
class CafeInfoFragment : BaseFragment<FragmentCafeInfoBinding>() {
  override fun getViewBinding() = FragmentCafeInfoBinding.inflate(layoutInflater)

  private val viewModel: CafeDetailViewModel by hiltNavGraphViewModels(R.id.nav_main)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    binding.apply {
      tvCafeInfoAreaValue.text = "30 m²"
      tvCafeInfoMeetingRoomValue.text = "3"
      tvCafeInfoMultiSeatValue.text = "24"
      tvCafeInfoSingleSeatValue.text = "1"
      // TODO 이거 확장 축소 가능한 요일별 스피너로 구현해야 함
      tvCafeInfoBusinessHoursValue.text = "매일 09:00 - 21:00"
      tvCafeInfoToiletValue.text = "내부 / 남녀 분리 / 장애인 화장실 있음"
      tvCafeInfoParkingValue.text = "가능 / 기본 1시간 / 시간당 3,000원"
      ivCafeInfoSmokingValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_x_colored_16)
      ivCafeInfoWifiValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_o_filled_16)
      ivCafeInfoPlugValue.setImageResource(us.wedemy.eggeum.android.design.R.drawable.ic_o_filled_16)
      tvCafeInfoPhoneChargingValue.text = "카운터에서 가능"
      tvCafeInfoPhoneNumber.text = "02-123-4567"
      tvCafeInfoSnsValue.text = "블로그, 인스타그램, 웹사이트"
    }
  }
}
