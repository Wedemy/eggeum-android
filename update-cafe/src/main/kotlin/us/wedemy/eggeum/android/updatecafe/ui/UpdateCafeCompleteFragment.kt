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
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentUpdateCafeCompleteBinding
import us.wedemy.eggeum.android.updatecafe.viewmodel.UpdateCafeCompleteViewModel

@AndroidEntryPoint
class UpdateCafeCompleteFragment : BaseFragment<FragmentUpdateCafeCompleteBinding>() {
  override fun getViewBinding() = FragmentUpdateCafeCompleteBinding.inflate(layoutInflater)

  private val viewModel by viewModels<UpdateCafeCompleteViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initListener()
    initObserver()
  }

  private fun initListener() {
    // TODO
  }

  private fun initObserver() {
    // TODO
  }
}
