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
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentCafeImageBinding
import us.wedemy.eggeum.android.main.ui.adapter.CafeImageAdapter
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel

interface CafeImageClickListener {
  fun onItemClick(position: Int)
}

@AndroidEntryPoint
class CafeImageFragment : BaseFragment<FragmentCafeImageBinding>() {
  override fun getViewBinding() = FragmentCafeImageBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<CafeDetailViewModel>()

  private val cafeImageAdapter by lazy {
    CafeImageAdapter(
      object : CafeImageClickListener {
        override fun onItemClick(position: Int) {
          // TODO CafeImageDetail 화면 으로 이동하는 이벤트 구현
        }
      },
    )
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initObserver()
  }

  private fun initView() {
    binding.rvCafeImage.apply {
      setHasFixedSize(true)
      adapter = cafeImageAdapter
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.cafeDetailInfo.collect { cafeDetailInfo ->
          cafeImageAdapter.replaceAll(cafeDetailInfo.image?.files)
        }
      }
    }
  }
}
