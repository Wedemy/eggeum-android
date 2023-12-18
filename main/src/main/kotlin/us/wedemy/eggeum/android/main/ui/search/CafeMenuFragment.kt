/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.os.Bundle
import android.view.View
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.addDivider
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.FragmentCafeMenuBinding
import us.wedemy.eggeum.android.main.ui.MainActivity
import us.wedemy.eggeum.android.main.ui.adapter.CafeMenuAdapter
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel

@AndroidEntryPoint
class CafeMenuFragment : BaseFragment<FragmentCafeMenuBinding>() {
  override fun getViewBinding() = FragmentCafeMenuBinding.inflate(layoutInflater)

  private val viewModel: CafeDetailViewModel by hiltNavGraphViewModels(R.id.nav_main)

  private val cafeMenuAdapter by lazy { CafeMenuAdapter() }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initObserver()
  }

  private fun initView() {
    binding.rvCafeMenu.apply {
      setHasFixedSize(true)
      adapter = cafeMenuAdapter
      addDivider(us.wedemy.eggeum.android.design.R.color.gray_300)
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.navigateToUpdateCafeEvent.collect{
          (activity as MainActivity).navigateToUpdateCafe()
        }
      }
    }
  }
}
