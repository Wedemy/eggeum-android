/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.myaccount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.textChangesAsFlow
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentInquiryBinding
import us.wedemy.eggeum.android.main.viewmodel.InquiryViewModel

@AndroidEntryPoint
class InquiryFragment : BaseFragment<FragmentInquiryBinding>() {
  override fun getViewBinding() = FragmentInquiryBinding.inflate(layoutInflater)

  private val viewModel by viewModels<InquiryViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
    initObserver()
  }

  private fun initListener() {
    with(binding) {
      ivInquiryClose.setOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      btnSendInquiry.setOnClickListener {
        // TODO 문의하기 구현
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        val inquiryTitleEditTextFlow = binding.tietInquiryTitle.textChangesAsFlow()
        inquiryTitleEditTextFlow.collect { text ->
          val inquiryTitle = text.toString().trim()
          viewModel.setInquiryTitle(inquiryTitle)
        }
      }

      launch {
        val inquiryContentEditTextFlow = binding.tietInquiryContent.textChangesAsFlow()
        inquiryContentEditTextFlow.collect { text ->
          val inquiryContent = text.toString().trim()
          viewModel.setInquiryContent(inquiryContent)
        }
      }

      launch {
        viewModel.enableSendInquiry.collect { flag ->
          Timber.tag("InquiryFragment").d("$flag")
          binding.btnSendInquiry.isEnabled = flag
        }
      }
    }
  }
}
