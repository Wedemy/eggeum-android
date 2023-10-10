/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.myaccount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentWithdrawBinding
import us.wedemy.eggeum.android.main.ui.MainActivity
import us.wedemy.eggeum.android.main.viewmodel.WithdrawViewModel

@AndroidEntryPoint
class WithdrawFragment : BaseFragment<FragmentWithdrawBinding>() {
  override fun getViewBinding() = FragmentWithdrawBinding.inflate(layoutInflater)

  private val viewModel by viewModels<WithdrawViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
    initObserver()
  }

  private fun initListener() {
    with(binding) {
      ivWithdrawClose.setOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      clWithdrawAgreeToNotification.setOnClickListener {
        viewModel.setAgreeToWithdraw()
      }

      btnWithdraw.setOnClickListener {
        viewModel.withdraw()
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.agreeToWithdraw.collect { isChecked ->
          binding.cbWithdrawAgreeToNotification.isChecked = isChecked
          binding.btnWithdraw.isEnabled = isChecked
        }
      }

      launch {
        viewModel.navigateToLoginEvent.collect {
          (activity as MainActivity).navigateToLogin()
        }
      }
    }
  }
}
