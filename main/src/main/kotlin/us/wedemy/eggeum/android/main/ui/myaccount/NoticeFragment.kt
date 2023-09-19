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
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentNoticeBinding
import us.wedemy.eggeum.android.main.ui.adapter.NoticeAdapter
import us.wedemy.eggeum.android.main.ui.item.NoticeItem
import us.wedemy.eggeum.android.main.viewmodel.NoticeViewModel

@AndroidEntryPoint
class NoticeFragment : BaseFragment<FragmentNoticeBinding>() {
  override fun getViewBinding() = FragmentNoticeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<NoticeViewModel>()

  @Suppress("unused")
  private val noticeItemClickListener = NoticeItemClickListener(
    onSearchTextfieldClick = {},
    onNoticeItemClick = {}
  )

  private lateinit var noticeAdapter: NoticeAdapter

  private val notices = listOf(
    NoticeUiModel.NoticeTitleItem,
    NoticeUiModel.NoticeSearchItem,
    NoticeUiModel.NoticeListItem(
      NoticeItem(
        "[공지] 스티커 출시",
        "2023. 10. 10",
        "공지 내용이 하단에 노출됩니다.\n검색 기능이 있지만 앱 내 스크롤 최소화로 인해 Height\n최대 사이즈 픽스 후 스크롤 됩니다.\n날짜 노출 or 날짜,시간 노출",
      )
    ),
    NoticeUiModel.NoticeListItem(
      NoticeItem(
        "[공지] 버전 업데이트 v1.1",
        "2023. 10. 10",
        "공지 내용이 하단에 노출됩니다.\n검색 기능이 있지만 앱 내 스크롤 최소화로 인해 Height\n최대 사이즈 픽스 후 스크롤 됩니다.\n날짜 노출 or 날짜,시간 노출",
      )
    ),
    NoticeUiModel.NoticeListItem(
      NoticeItem(
        "[공지] 공부하기 좋은 카페 찾는 법",
        "2023. 10. 10",
        "공지 내용이 하단에 노출됩니다.\n검색 기능이 있지만 앱 내 스크롤 최소화로 인해 Height\n최대 사이즈 픽스 후 스크롤 됩니다.\n날짜 노출 or 날짜,시간 노출",
      )
    ),
    NoticeUiModel.NoticeListItem(
      NoticeItem(
        "[공지] 이끔 신규 오픈 이벤트",
        "2023. 10. 10",
        "공지 내용이 하단에 노출됩니다.\n검색 기능이 있지만 앱 내 스크롤 최소화로 인해 Height\n최대 사이즈 픽스 후 스크롤 됩니다.\n날짜 노출 or 날짜,시간 노출",
      )
    ),
    NoticeUiModel.NoticeListItem(
      NoticeItem(
        "[공지] 카페 평가하는 법",
        "2023. 10. 10",
        "공지 내용이 하단에 노출됩니다.\n검색 기능이 있지만 앱 내 스크롤 최소화로 인해 Height\n최대 사이즈 픽스 후 스크롤 됩니다.\n날짜 노출 or 날짜,시간 노출",
      )
    ),
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    noticeAdapter = NoticeAdapter(
      notices.ifEmpty {
        listOf(NoticeUiModel.NoticeEmptyItem)
      },
    )

    binding.rvNotice.apply {
      setHasFixedSize(true)
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
//        val searchKeywordEditTextFlow = binding.tietNotice.textChangesAsFlow()
//        searchKeywordEditTextFlow.collect { text ->
//          val searchKeyword = text.toString().trim()
//          viewModel.setSearchKeyword(searchKeyword)
//        }
      }

      launch {
        viewModel.searchKeyword.collect {
          // TODO searchKeyword 를 통한 공지사항 리스트 필터링 구현
        }
      }
    }
  }
}
