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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.common.extension.addDivider
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.textChangesAsFlow
import us.wedemy.eggeum.android.design.R
import us.wedemy.eggeum.android.main.databinding.FragmentNoticeBinding
import us.wedemy.eggeum.android.main.ui.adapter.NoticeAdapter
import us.wedemy.eggeum.android.main.viewmodel.NoticeViewModel

@AndroidEntryPoint
class NoticeFragment : BaseFragment<FragmentNoticeBinding>() {
  override fun getViewBinding() = FragmentNoticeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<NoticeViewModel>()

  private val noticeAdapter by lazy { NoticeAdapter() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    binding.rvNotice.apply {
      addDivider(R.color.gray_100)
      adapter = noticeAdapter
    }
  }

  private fun initListener() {
    with(binding) {
      tbNotice.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.noticeList.collectLatest { notices ->
          noticeAdapter.submitData(notices)
        }
      }

      launch {
        val editTextFlow = binding.tietNotice.textChangesAsFlow()
        editTextFlow.collect { text ->
          val query = text.toString().trim()
          viewModel.setSearchQuery(query)
        }
      }
    }
  }
}
