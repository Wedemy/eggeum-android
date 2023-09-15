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
import us.wedemy.eggeum.android.common.extension.addDivider
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.textChangesAsFlow
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentNoticeBinding
import us.wedemy.eggeum.android.main.ui.adapter.NoticeAdapter
import us.wedemy.eggeum.android.main.ui.item.NoticeItem
import us.wedemy.eggeum.android.main.viewmodel.NoticeViewModel

@AndroidEntryPoint
class NoticeFragment : BaseFragment<FragmentNoticeBinding>() {
  override fun getViewBinding() = FragmentNoticeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<NoticeViewModel>()

  private lateinit var noticeAdapter: NoticeAdapter

  private val notices = listOf(
    NoticeItem("공부하기 좋은 카페 찾는 법", "공지 내용"),
    NoticeItem("카페 평가하는 법", "공지 내용"),
    NoticeItem("일이삼사오육칠팔구십일이삼사오육", "공지 내용"),
    NoticeItem("공부하기 좋은 카페 찾는 법", "공지 내용"),
    NoticeItem("카페 평가하는 법", "공지 내용"),
    NoticeItem("일이삼사오육칠팔구십일이삼사오육", "공지 내용"),
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    noticeAdapter = NoticeAdapter(notices)

    binding.rvNotice.apply {
      setHasFixedSize(true)
      adapter = noticeAdapter
      addDivider(us.wedemy.eggeum.android.design.R.color.gray_300)
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
        val searchKeywordEditTextFlow = binding.tietNotice.textChangesAsFlow()
        searchKeywordEditTextFlow.collect { text ->
          val searchKeyword = text.toString().trim()
          viewModel.setSearchKeyword(searchKeyword)
        }
      }

      launch {
        viewModel.searchKeyword.collect {
          // TODO searchKeyword 를 통한 공지사항 리스트 필터링 구현
        }
      }
    }
  }
}
