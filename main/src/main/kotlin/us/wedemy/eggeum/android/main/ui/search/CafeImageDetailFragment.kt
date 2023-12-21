/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentCafeImageDetailBinding
import us.wedemy.eggeum.android.main.ui.adapter.CafeImageDetailAdapter
import us.wedemy.eggeum.android.main.viewmodel.CafeImageDetailViewModel

@AndroidEntryPoint
class CafeImageDetailFragment : BaseFragment<FragmentCafeImageDetailBinding>() {

  private val viewModel by viewModels<CafeImageDetailViewModel>()

  private val cafeImageDetailAdapter by lazy {
    CafeImageDetailAdapter(viewModel.cafeImages.files)
  }

  override fun getViewBinding() = FragmentCafeImageDetailBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initListener()
  }
  private fun initView() {
    binding.vpCafeImageDetail.apply {
      adapter = cafeImageDetailAdapter
      setCurrentItem(viewModel.currentPosition, false)
    }
  }

  private fun initListener() {
    with(binding) {
      tbCafeImageDetail.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      ivCafeImageDetailNext.setOnClickListener {
        val currentItem = vpCafeImageDetail.currentItem
        vpCafeImageDetail.setCurrentItem(currentItem + 1, true)
      }

      ivCafeImageDetailPrev.setOnClickListener {
        val currentItem = vpCafeImageDetail.currentItem
        if (currentItem > 0) {
          vpCafeImageDetail.setCurrentItem(currentItem - 1, true)
        } else {
          // 이미지가 존재해야 해당 화면에 접근할 수 있으므로 imageUrlList 의 사이즈는 0 이 될 수 없음
          val lastIndex = (vpCafeImageDetail.adapter?.itemCount ?: 0) / viewModel.cafeImages.files.size - 1
          vpCafeImageDetail.setCurrentItem(lastIndex * viewModel.cafeImages.files.size, true)
        }
      }
    }
  }
}
