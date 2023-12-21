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
import us.wedemy.eggeum.android.common.ui.BaseDialogFragment
import us.wedemy.eggeum.android.main.databinding.FragmentReportCafeImageBinding
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel

// TOOD 사진 신고하기 API 연동
@AndroidEntryPoint
class ReportCafeImageFragment : BaseDialogFragment<FragmentReportCafeImageBinding>() {
  override fun getViewBinding() = FragmentReportCafeImageBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<CafeDetailViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
    initObserver()
  }

  private fun initListener() {

  }

  private fun initObserver() {
    repeatOnStarted {
      launch {

      }
    }
  }
}
