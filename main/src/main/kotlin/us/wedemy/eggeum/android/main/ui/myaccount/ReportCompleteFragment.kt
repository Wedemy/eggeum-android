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
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentReportCompleteBinding

class ReportCompleteFragment : BaseFragment<FragmentReportCompleteBinding>() {
  override fun getViewBinding() = FragmentReportCompleteBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initListener()
  }

  private fun initListener() {
    binding.btnReportComplete.setOnClickListener {
      val action = ReportCompleteFragmentDirections.actionFragmentReportCompleteToFragmentMyAccount()
      findNavController().safeNavigate(action)
    }
  }
}
