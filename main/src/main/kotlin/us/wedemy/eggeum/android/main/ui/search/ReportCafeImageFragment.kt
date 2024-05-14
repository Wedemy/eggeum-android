/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.base.BaseDialogFragment
import us.wedemy.eggeum.android.main.databinding.FragmentReportCafeImageBinding
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel

// 사진 신고하기 API 연동
@AndroidEntryPoint
class ReportCafeImageFragment : BaseDialogFragment<FragmentReportCafeImageBinding>() {
  override fun getViewBinding() = FragmentReportCafeImageBinding.inflate(layoutInflater)

  @Suppress("unused")
  private val viewModel by activityViewModels<CafeDetailViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
    initObserver()
  }

  private fun initListener() {
    // 체크 박스 클릭 이벤트
    // 버튼 클릭을 통한 사진 신고하기 API 호출
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        // 화면 이동 이벤트 구독
      }
    }
  }
}
