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
import us.wedemy.eggeum.android.common.extension.addDivider
import us.wedemy.eggeum.android.design.R
import us.wedemy.eggeum.android.main.databinding.FragmentCafeMenuBinding
import us.wedemy.eggeum.android.main.ui.adapter.CafeMenuAdapter

@AndroidEntryPoint
class CafeMenuFragment : BaseFragment<FragmentCafeMenuBinding>() {
  override fun getViewBinding() = FragmentCafeMenuBinding.inflate(layoutInflater)

  private val cafeMenuAdapter by lazy { CafeMenuAdapter() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    binding.rvCafeMenu.apply {
      setHasFixedSize(true)
      adapter = cafeMenuAdapter
      addDivider(R.color.gray_300)
    }
  }
}
