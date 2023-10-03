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
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentSelectOpinionBinding
import us.wedemy.eggeum.android.updatecafe.viewmodel.SelectOpinionViewModel

@AndroidEntryPoint
class SelectOpinionFragment : BaseFragment<FragmentSelectOpinionBinding>() {
  override fun getViewBinding() = FragmentSelectOpinionBinding.inflate(layoutInflater)

  private val viewModel by viewModels<SelectOpinionViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initListener()
    initObserver()
  }

  private fun initListener() {
    with(binding) {
      tbSelectOpinion.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      clRequestCorrectionOfInfo.setOnClickListener {
        viewModel.setCbAgreeToRequestCorrectionOfInfo()
      }

      btnSelectInfoCategories.setOnClickListener {
        val action = SelectOpinionFragmentDirections.actionFragmentSelectOpinionToFragmentSelectInfoCategories()
        findNavController().safeNavigate(action)
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.agreeToRequestCorrectionOfInfo.collect { isChecked ->
          binding.cbRequestCorrectionOfInfo.isChecked = isChecked
          binding.btnSelectInfoCategories.isEnabled = isChecked
        }
      }
    }
  }
}
