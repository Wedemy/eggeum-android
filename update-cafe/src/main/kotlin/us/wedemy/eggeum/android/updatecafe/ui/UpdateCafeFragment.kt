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
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentUpdateCafeBinding
import us.wedemy.eggeum.android.updatecafe.viewmodel.UpdateCafeViewModel

@AndroidEntryPoint
class UpdateCafeFragment : BaseFragment<FragmentUpdateCafeBinding>() {
  override fun getViewBinding() = FragmentUpdateCafeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<UpdateCafeViewModel>()

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
