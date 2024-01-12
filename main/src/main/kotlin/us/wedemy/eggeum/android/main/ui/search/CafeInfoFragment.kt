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
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
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
      Timber.e("Invalid address or no available browser: $uri")
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
      tvCafeInfoAreaValue.text = formatAreaSize(cafeDetailInfo.areaSize)
      tvCafeInfoMeetingRoomValue.text = (cafeDetailInfo.meetingRoomCount ?: "").toString()
      tvCafeInfoMultiSeatValue.text = (cafeDetailInfo.multiSeatCount ?: "").toString()
      tvCafeInfoSingleSeatValue.text = (cafeDetailInfo.singleSeatCount ?: "").toString()
      // TODO: 확장 축소 가능한 요일별 리스트 구현
      tvCafeInfoBusinessHoursValue.text = "매일 09:00 - 21:00"
      tvCafeInfoRestRoomValue.text = cafeDetailInfo.restRoom
      tvCafeInfoParkingValue.text = cafeDetailInfo.parking
      updateSmokingAreaIcon(cafeDetailInfo.existsSmokingArea)
      updateWifiIcon(cafeDetailInfo.existsWifi)
      updateOutletIcon(cafeDetailInfo.existsOutlet)
      tvCafeInfoMobileChargingValue.text = cafeDetailInfo.mobileCharging
      tvCafeInfoPhoneNumber.text = cafeDetailInfo.phone
      updateSocialMediaLinks(cafeDetailInfo)
    }
  }

  private fun formatAreaSize(areaSize: String?): String {
    return if (areaSize.isNullOrEmpty()) "" else "$areaSize m²"
  }

  private fun updateIcon(imageView: ImageView, exists: Boolean?) {
    imageView.setImageResource(
      if (exists == true) us.wedemy.eggeum.android.design.R.drawable.ic_o_filled_16
      else us.wedemy.eggeum.android.design.R.drawable.ic_x_colored_16,
    )
  }

  private fun updateSocialMediaLinks(cafeDetailInfo: InfoModel) {
    binding.apply {
      tvCafeInfoBlogValue.text =
        cafeDetailInfo.blogUri.takeUnless { it.isNullOrEmpty() }?.let { getString(R.string.blog) } ?: ""
      tvCafeInfoInstagramValue.text =
        cafeDetailInfo.instagramUri.takeUnless { it.isNullOrEmpty() }?.let { getString(R.string.instagram) } ?: ""
      tvCafeInfoWebsiteValue.text =
        cafeDetailInfo.websiteUri.takeUnless { it.isNullOrEmpty() }?.let { getString(R.string.website) } ?: ""
    }
    updateLinkDots(cafeDetailInfo)
  }

  private fun updateLinkDots(cafeDetailInfo: InfoModel) {
    binding.apply {
      tvCafeInfoDotFirst.visibility =
        if (!cafeDetailInfo.blogUri.isNullOrEmpty() && !cafeDetailInfo.instagramUri.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
      tvCafeInfoDotSecond.visibility =
        if (!cafeDetailInfo.instagramUri.isNullOrEmpty() && !cafeDetailInfo.websiteUri.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
      tvCafeInfoDotThird.visibility =
        if (!cafeDetailInfo.blogUri.isNullOrEmpty() && !cafeDetailInfo.websiteUri.isNullOrEmpty() &&
          cafeDetailInfo.instagramUri.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
    }
  }

  private fun updateSmokingAreaIcon(existsSmokingArea: Boolean?) {
    updateIcon(binding.ivCafeInfoSmokingValue, existsSmokingArea)
  }

  private fun updateWifiIcon(existsWifi: Boolean?) {
    updateIcon(binding.ivCafeInfoWifiValue, existsWifi)
  }

  private fun updateOutletIcon(existsOutlet: Boolean?) {
    updateIcon(binding.ivCafeInfoOutletValue, existsOutlet)
  }
}
