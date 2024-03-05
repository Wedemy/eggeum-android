/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.myaccount

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.InsetterApplyTypeDsl
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.extension.textChangesAsFlow
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentReportBinding
import us.wedemy.eggeum.android.main.viewmodel.ReportViewModel

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding>() {
  override fun getViewBinding() = FragmentReportBinding.inflate(layoutInflater)

  private val viewModel by viewModels<ReportViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.root.applyInsetter {
      type(
        ime = false,
        statusBars = false,
        navigationBars = true,
        f = InsetterApplyTypeDsl::padding,
      )
    }
    initListener()
    initObserver()
  }

  private fun initListener() {
    with(binding) {
      ivReportClose.setOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      btnSendReport.setOnClickListener {
        viewModel.createReport()
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        val reportTitleEditTextFlow = binding.tietReportTitle.textChangesAsFlow()
        reportTitleEditTextFlow.collect { text ->
          val reportTitle = text.toString().trim()
          viewModel.setReportTitle(reportTitle)
        }
      }

      launch {
        val reportContentEditTextFlow = binding.tietReportContent.textChangesAsFlow()
        reportContentEditTextFlow.collect { text ->
          val reportContent = text.toString().trim()
          viewModel.setReportContent(reportContent)
        }
      }

      launch {
        viewModel.enableSendReport.collect { flag ->
          binding.btnSendReport.isEnabled = flag
        }
      }

      launch {
        viewModel.navigateToReportCompleteEvent.collect {
          val action = ReportFragmentDirections.actionFragmentReportToFragmentReportComplete()
          findNavController().safeNavigate(action)
        }
      }

      launch {
        viewModel.showToastEvent.collect { message ->
          Toast.makeText(requireContext(), message.asString(requireContext()), Toast.LENGTH_SHORT).show()
        }
      }
    }
  }
}
