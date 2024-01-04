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
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentUpdateMenuCompleteBinding

@AndroidEntryPoint
class UpdateMenuCompleteFragment : BaseFragment<FragmentUpdateMenuCompleteBinding>() {
  override fun getViewBinding() = FragmentUpdateMenuCompleteBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initListener()
  }

  private fun initListener() {
    // TODO
    binding.btnRegisterCafeComplete.setOnClickListener {
      val action = UpdateMenuCompleteFragmentDirections.actionFragmentUpdateMenuCompleteToFragmentSelectInfoCategories()
      findNavController().safeNavigate(action)
    }
  }
}
