/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.myaccount

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.FragmentSettingBinding
import us.wedemy.eggeum.android.main.ui.MainActivity
import us.wedemy.eggeum.android.main.viewmodel.SettingViewModel

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
  override fun getViewBinding() = FragmentSettingBinding.inflate(layoutInflater)

  private val viewModel by viewModels<SettingViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
    initObserver()
  }

  private fun initListener() {
    with(binding) {
      tbSetting.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      clSettingLogout.setOnClickListener {
        viewModel.logout()
      }

      clSettingWithdraw.setOnClickListener {
        val action = SettingFragmentDirections.actionFragmentSettingToFragmentWithdraw()
        findNavController().safeNavigate(action)
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.navigateToLoginEvent.collect {
          Toast.makeText(requireContext(), getString(R.string.logout_complete), Toast.LENGTH_SHORT).show()
          (activity as MainActivity).navigateToLogin()
        }
      }
    }
  }
}
