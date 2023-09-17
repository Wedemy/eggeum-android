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
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentMyAccountBinding
import us.wedemy.eggeum.android.main.viewmodel.MyAccountViewModel

@AndroidEntryPoint
class MyAccountFragment : BaseFragment<FragmentMyAccountBinding>() {
  override fun getViewBinding() = FragmentMyAccountBinding.inflate(layoutInflater)

  private val viewModel by viewModels<MyAccountViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
    initObserver()
  }

  private fun initListener() {
    with(binding) {
      clMyAccountSetting.setOnClickListener {
        val action = MyAccountFragmentDirections.actionFragmentMyAccountToFragmentSetting()
        findNavController().safeNavigate(action)
      }

      clMyAccountCustomerSupport.setOnClickListener {
        val action = MyAccountFragmentDirections.actionFragmentMyAccountToFragmentInquiry()
        findNavController().safeNavigate(action)
      }

      clMyAccountNotice.setOnClickListener {
        val action = MyAccountFragmentDirections.actionFragmentMyAccountToFragmentNotice()
        findNavController().safeNavigate(action)
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.showToastEvent.collect { message ->
          Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
      }
    }
  }
}
