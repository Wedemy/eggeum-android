/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dev.chrisbanes.insetter.InsetterApplyTypeDsl
import dev.chrisbanes.insetter.applyInsetter

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
  private var _binding: VB? = null
  protected val binding: VB
    get() = _binding!!

  abstract fun getViewBinding(): VB

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ) = getViewBinding().also {
    _binding = it
    binding.root.applyInsetter {
      type(navigationBars = true, f = InsetterApplyTypeDsl::padding)
      type(statusBars = true, f = InsetterApplyTypeDsl::padding)
    }
  }.root

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}
