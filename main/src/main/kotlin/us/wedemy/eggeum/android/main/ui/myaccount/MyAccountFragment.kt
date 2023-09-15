/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.myaccount

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.main.databinding.FragmentMyAccountBinding
import us.wedemy.eggeum.android.common.ui.BaseFragment

@AndroidEntryPoint
class MyAccountFragment : BaseFragment<FragmentMyAccountBinding>() {
  override fun getViewBinding() = FragmentMyAccountBinding.inflate(layoutInflater)

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
    // TODO 프로필 정보 및 버전 정보 가져오기
  }
}
