/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("unused", "UnusedPrivateProperty")

package us.wedemy.eggeum.android.updatecafe.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentInputCafeMenuBinding
import us.wedemy.eggeum.android.updatecafe.viewmodel.InputCafeMenuViewModel

@AndroidEntryPoint
class InputCafeMenuFragment : BaseFragment<FragmentInputCafeMenuBinding>() {
  override fun getViewBinding() = FragmentInputCafeMenuBinding.inflate(layoutInflater)

  private val viewModel by viewModels<InputCafeMenuViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initListener()
    initObserver()
  }

  private fun initListener() {
    with(binding) {
      btnUpdateMenuComplete.setOnClickListener {
        // TODO: 다시 이전 페이지로 돌아가기
      }
    }
  }

  private fun initObserver() {
    // TODO
  }
}
