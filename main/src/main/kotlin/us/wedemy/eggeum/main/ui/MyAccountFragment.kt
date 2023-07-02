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
import us.wedemy.eggeum.main.databinding.FragmentMyAccountBinding
import us.wedemy.eggeum.main.viewmodel.MyAccountViewModel

@AndroidEntryPoint
class MyAccountFragment : BaseFragment<FragmentMyAccountBinding>() {
  override fun getViewBinding() = FragmentMyAccountBinding.inflate(layoutInflater)

  private val viewModel by viewModels<MyAccountViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
    initObserver()
  }

  private fun initListener() {}

  private fun initObserver() {}
}
