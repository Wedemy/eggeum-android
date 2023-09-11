/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.myaccount

import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentInquiryBinding

@AndroidEntryPoint
class InquiryFragment : BaseFragment<FragmentInquiryBinding>() {
  override fun getViewBinding() = FragmentInquiryBinding.inflate(layoutInflater)
}
