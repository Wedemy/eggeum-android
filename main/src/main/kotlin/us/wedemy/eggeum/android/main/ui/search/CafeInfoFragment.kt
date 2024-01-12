/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.model.InfoModel
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.FragmentCafeInfoBinding
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel

@AndroidEntryPoint
class CafeInfoFragment : BaseFragment<FragmentCafeInfoBinding>() {
  override fun getViewBinding() = FragmentCafeInfoBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<CafeDetailViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    viewModel.cafeDetailInfo.value.info?.let { updateCafeInfo(it) }
  }

  private fun initListener() {
    with(binding) {
      tvCafeInfoBlogValue.setOnClickListener {
        startWebBrowser(viewModel.cafeDetailInfo.value.info?.blogUri)
      }

      tvCafeInfoInstagramValue.setOnClickListener {
        startWebBrowser(viewModel.cafeDetailInfo.value.info?.instagramUri)
      }

      tvCafeInfoWebsiteValue.setOnClickListener {
        startWebBrowser(viewModel.cafeDetailInfo.value.info?.websiteUri)
      }
    }
  }

  private fun startWebBrowser(uri: String?) {
    try {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
      startActivity(intent)
    } catch (e: ActivityNotFoundException) {
      Toast.makeText(requireContext(), getString(R.string.invalid_address), Toast.LENGTH_SHORT).show()
    }
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
      if (cafeDetailInfo.blogUri.isNullOrEmpty()) {
        tvCafeInfoBlogValue.text = ""
      } else {
        tvCafeInfoBlogValue.text = getString(R.string.blog)
      }
      if (cafeDetailInfo.instagramUri.isNullOrEmpty()) {
        tvCafeInfoInstagramValue.text = ""
      } else {
        tvCafeInfoInstagramValue.text = getString(R.string.instagram)
      }
      if (cafeDetailInfo.websiteUri.isNullOrEmpty()) {
        tvCafeInfoWebsiteValue.text = ""
      } else {
        tvCafeInfoWebsiteValue.text = getString(R.string.website)
      }
      if (!cafeDetailInfo.blogUri.isNullOrEmpty() && !cafeDetailInfo.instagramUri.isNullOrEmpty()) {
        tvCafeInfoDotFirst.visibility = View.VISIBLE
      } else {
        tvCafeInfoDotFirst.visibility = View.INVISIBLE
      }
      if (!cafeDetailInfo.instagramUri.isNullOrEmpty() && !cafeDetailInfo.websiteUri.isNullOrEmpty()) {
        tvCafeInfoDotSecond.visibility = View.VISIBLE
      } else {
        tvCafeInfoDotSecond.visibility = View.INVISIBLE
      }
      if (!cafeDetailInfo.blogUri.isNullOrEmpty() && !cafeDetailInfo.websiteUri.isNullOrEmpty() && cafeDetailInfo.instagramUri.isNullOrEmpty()) {
        tvCafeInfoDotThird.visibility = View.VISIBLE
      } else {
        tvCafeInfoDotThird.visibility = View.INVISIBLE
      }
    }
  }
}
