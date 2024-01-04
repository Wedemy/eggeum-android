/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.onboard.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.onboard.databinding.FragmentServiceTermsBinding

class ServiceTermsFragment : BaseFragment<FragmentServiceTermsBinding>() {
  override fun getViewBinding() = FragmentServiceTermsBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
  }

  private fun initListener() {
    binding.tbServiceTerms.setNavigationOnClickListener {
      if (!findNavController().navigateUp()) {
        requireActivity().finish()
      }
    }

    binding.btnServiceTermsCheck.setOnClickListener {
      if (!findNavController().navigateUp()) {
        requireActivity().finish()
      }
    }
  }
}
