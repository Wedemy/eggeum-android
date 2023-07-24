/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<VB : ViewBinding> : BottomSheetDialogFragment() {
  private var _binding: VB? = null
  protected val binding: VB
    get() = _binding!!

  abstract fun getViewBinding(): VB

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View = getViewBinding().also { _binding = it }.root

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}
