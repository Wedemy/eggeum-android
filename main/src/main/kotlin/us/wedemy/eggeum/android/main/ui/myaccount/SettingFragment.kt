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
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentSettingBinding

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
  override fun getViewBinding() = FragmentSettingBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
  }

  private fun initListener() {
    with(binding) {
      tbSetting.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      clSettingLogout.setOnClickListener {
        //TODO 로그아웃 구현
      }

      clSettingWithdraw.setOnClickListener {
        val action = SettingFragmentDirections.actionFragmentSettingToFragmentWithdraw()
        findNavController().safeNavigate(action)
      }
    }
  }
}