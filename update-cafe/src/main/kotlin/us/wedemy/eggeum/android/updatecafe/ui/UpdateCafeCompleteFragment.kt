/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.updatecafe.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentUpdateCafeCompleteBinding

@AndroidEntryPoint
class UpdateCafeCompleteFragment : BaseFragment<FragmentUpdateCafeCompleteBinding>() {
  override fun getViewBinding() = FragmentUpdateCafeCompleteBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initListener()
  }

  private fun initListener() {
    binding.btnRegisterCafeComplete.setOnClickListener {
      if (!findNavController().navigateUp()) {
        requireActivity().finish()
      }
    }
  }
}
