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
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentNoticeBinding
import us.wedemy.eggeum.android.main.ui.adapter.NoticeAdapter
import us.wedemy.eggeum.android.main.model.NoticeModel
import us.wedemy.eggeum.android.main.viewmodel.NoticeViewModel

@Suppress("unused")
@AndroidEntryPoint
class NoticeFragment : BaseFragment<FragmentNoticeBinding>() {
  override fun getViewBinding() = FragmentNoticeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<NoticeViewModel>()

  private val noticeItemClickListener = NoticeItemClickListener(
    onSearchTextfieldClick = {
      // TODO 검색어를 통한 필터링 구현
    },
  )

  private val noticeAdapter by lazy {
    NoticeAdapter(noticeItemClickListener)
  }

  private val notices = listOf(
    NoticeUiModel.NoticeTitleItem,
    NoticeUiModel.NoticeSearchItem,
    NoticeUiModel.NoticeListItem(
      NoticeModel(
        "[공지] 스티커 출시",
        "2023. 10. 10",
        "공지 내용이 하단에 노출됩니다.\n검색 기능이 있지만 앱 내 스크롤 최소화로 인해 Height\n최대 사이즈 픽스 후 스크롤 됩니다.\n날짜 노출 or 날짜,시간 노출",
      ),
    ),
    NoticeUiModel.NoticeListItem(
      NoticeModel(
        "[공지] 버전 업데이트 v1.1",
        "2023. 10. 10",
        "공지 내용이 하단에 노출됩니다.\n검색 기능이 있지만 앱 내 스크롤 최소화로 인해 Height\n최대 사이즈 픽스 후 스크롤 됩니다.\n날짜 노출 or 날짜,시간 노출",
      ),
    ),
    NoticeUiModel.NoticeListItem(
      NoticeModel(
        "[공지] 공부하기 좋은 카페 찾는 법",
        "2023. 10. 10",
        "공지 내용이 하단에 노출됩니다.\n검색 기능이 있지만 앱 내 스크롤 최소화로 인해 Height\n최대 사이즈 픽스 후 스크롤 됩니다.\n날짜 노출 or 날짜,시간 노출",
      ),
    ),
    NoticeUiModel.NoticeListItem(
      NoticeModel(
        "[공지] 이끔 신규 오픈 이벤트",
        "2023. 10. 10",
        "공지 내용이 하단에 노출됩니다.\n검색 기능이 있지만 앱 내 스크롤 최소화로 인해 Height\n최대 사이즈 픽스 후 스크롤 됩니다.\n날짜 노출 or 날짜,시간 노출",
      ),
    ),
    NoticeUiModel.NoticeListItem(
      NoticeModel(
        "[공지] 카페 평가하는 법",
        "2023. 10. 10",
        "공지 내용이 하단에 노출됩니다.\n검색 기능이 있지만 앱 내 스크롤 최소화로 인해 Height\n최대 사이즈 픽스 후 스크롤 됩니다.\n날짜 노출 or 날짜,시간 노출",
      ),
    ),
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    binding.rvNotice.adapter = noticeAdapter
    noticeAdapter.submitList(notices.ifEmpty { listOf(NoticeUiModel.NoticeEmptyItem) })
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
    // TODO 공지 목록의 변화를 구독
  }
}
