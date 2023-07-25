/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentCafeImageDetailBinding
import us.wedemy.eggeum.android.main.ui.adapter.CafeImageDetailAdapter

@AndroidEntryPoint
class CafeImageDetailFragment : BaseFragment<FragmentCafeImageDetailBinding>() {

  private val cafeImageDetailAdapter by lazy { CafeImageDetailAdapter() }

  override fun getViewBinding() = FragmentCafeImageDetailBinding.inflate(layoutInflater)

  // TODO CafeImageFragment 에서 imageUrlList 와 curretPosition 을 전달 받아야 함
  private val imageUrlList = emptyList<String>()
  private val currentPosition = 0

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.vpCafeImageDetail.apply {
      adapter = cafeImageDetailAdapter
      setCurrentItem(currentPosition, false)
    }

    with(binding) {
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
          val lastIndex = (vpCafeImageDetail.adapter?.itemCount ?: 0) / imageUrlList.size - 1
          vpCafeImageDetail.setCurrentItem(lastIndex * imageUrlList.size, true)
        }
      }
    }
  }
}
