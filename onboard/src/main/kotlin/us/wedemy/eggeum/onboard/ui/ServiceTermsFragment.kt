/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.onboard.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import us.wedemy.eggeum.onboard.R
import us.wedemy.eggeum.onboard.databinding.FragmentServiceTermsBinding
import us.wedemy.eggeum.onboard.ui.base.BaseFragment

class ServiceTermsFragment : BaseFragment<FragmentServiceTermsBinding>(R.layout.fragment_service_terms) {

  override fun getViewBinding() = FragmentServiceTermsBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initListener()
    initObserver()
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

  @Suppress("EmptyFunctionBlock")
  private fun initObserver() {
  }
}
