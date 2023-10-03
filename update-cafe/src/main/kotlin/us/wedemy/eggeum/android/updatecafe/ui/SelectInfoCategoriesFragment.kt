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
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentSelectInfoCategoriesBinding
import us.wedemy.eggeum.android.updatecafe.viewmodel.SelectInfoCategoriesViewModel

@AndroidEntryPoint
class SelectInfoCategoriesFragment : BaseFragment<FragmentSelectInfoCategoriesBinding>() {
  override fun getViewBinding() = FragmentSelectInfoCategoriesBinding.inflate(layoutInflater)

  private val viewModel by viewModels<SelectInfoCategoriesViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initListener()
    initObserver()
  }

  private fun initListener() {
    with(binding) {
      tbSelectInfoCategories.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      clInfo.setOnClickListener {
        viewModel.setCbAgreeToInfo()
      }

      clMenu.setOnClickListener {
        viewModel.setCbAgreeToMenu()
      }

      btnSelectCafeMenu.setOnClickListener {
        lateinit var action: NavDirections
        if (cbInfo.isChecked) {
          action = SelectInfoCategoriesFragmentDirections.actionSelectInfoCategoriesFragmentToInputCafeInfoFragment()
        }
        if (cbMenu.isChecked) {
          action = SelectInfoCategoriesFragmentDirections.actionSelectInfoCategoriesFragmentToSelectCafeMenuFragment()
        }
        findNavController().safeNavigate(action)
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.agreeToInfo.collect { isChecked ->
          binding.cbInfo.isChecked = isChecked

          if (binding.cbMenu.isChecked) binding.cbMenu.isChecked = !isChecked

          binding.btnSelectCafeMenu.isEnabled = binding.cbInfo.isChecked || binding.cbMenu.isChecked
        }
      }
      launch {
        viewModel.agreeToMenu.collect { isChecked ->
          binding.cbMenu.isChecked = isChecked

          if (binding.cbInfo.isChecked) binding.cbInfo.isChecked = !isChecked

          binding.btnSelectCafeMenu.isEnabled = binding.cbInfo.isChecked || binding.cbMenu.isChecked
        }
      }
    }
  }
}
