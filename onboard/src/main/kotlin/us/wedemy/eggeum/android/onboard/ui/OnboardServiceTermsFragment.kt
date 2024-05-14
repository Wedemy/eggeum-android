/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.onboard.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.onboard.BuildConfig
import us.wedemy.eggeum.android.onboard.databinding.FragmentOnboardServiceTermsBinding
import us.wedemy.eggeum.android.onboard.viewmodel.OnBoardViewModel

@AndroidEntryPoint
class OnboardServiceTermsFragment : BaseFragment<FragmentOnboardServiceTermsBinding>() {
  override fun getViewBinding() = FragmentOnboardServiceTermsBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<OnBoardViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initListener()
  }

  private fun initView() {
    binding.wvServiceTerms.apply {
      webViewClient = WebViewClient()
      webChromeClient = WebChromeClient()
      // 자바 스크립트 허용
      settings.javaScriptEnabled = true
      // 로컬 스토리지 허용
      settings.domStorageEnabled = true
      loadUrl(BuildConfig.SERVICE_TERMS_WEB_VIEW_URL)
    }
  }

  private fun initListener() {
    with(binding) {
      ivServiceTermsClose.setOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      btnServiceTermsCheck.setOnClickListener {
        viewModel.checkCbAgreeToServiceTerms()
        findNavController().navigateUp()
      }
    }
  }

  inner class WebViewClient : android.webkit.WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
      super.onPageStarted(view, url, favicon)
      binding.pbServiceTermsContentLoading.show()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
      super.onPageFinished(view, url)
      binding.pbServiceTermsContentLoading.hide()
    }
  }

  inner class WebChromeClient : android.webkit.WebChromeClient() {
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
      super.onProgressChanged(view, newProgress)
      binding.pbServiceTermsContentLoading.progress = newProgress
    }
  }

  // WebView 를 Fragment 에서 사용시 destroy 처리 필요
  override fun onDestroyView() {
    binding.wvServiceTerms.destroy()
    super.onDestroyView()
  }
}
