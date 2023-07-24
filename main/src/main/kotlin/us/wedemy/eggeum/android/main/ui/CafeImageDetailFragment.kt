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

// TODO 무한 스크롤을 지원해야 하므로 imageUrlList 의 size 가 1 보다 클 경우, list 의 맨 앞에 last 아이템을, 맨 뒤에 first 아이템을 추가 해야 함
@AndroidEntryPoint
class CafeImageDetailFragment : BaseFragment<FragmentCafeImageDetailBinding>() {

  private val cafeImageDetailAdapter by lazy { CafeImageDetailAdapter() }

  override fun getViewBinding() = FragmentCafeImageDetailBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    binding.vpCafeImageDetail.adapter = cafeImageDetailAdapter
  }
}
