/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.main.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.common.ui.BaseFragment
import us.wedemy.eggeum.main.databinding.FragmentHomeBinding
import us.wedemy.eggeum.main.viewmodel.HomeViewModel

//TODO 더미 데이터를 통한 화면 구성
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
  override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<HomeViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
    initObserver()
  }

  private fun initListener() {}

  private fun initObserver() {}
}
