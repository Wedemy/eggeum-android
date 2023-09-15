/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentCafeInfoBinding

@AndroidEntryPoint
class CafeInfoFragment : BaseFragment<FragmentCafeInfoBinding>() {
  override fun getViewBinding() = FragmentCafeInfoBinding.inflate(layoutInflater)
}
