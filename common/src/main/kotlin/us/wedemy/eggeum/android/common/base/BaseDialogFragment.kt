/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.base

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import us.wedemy.eggeum.android.common.extension.dp

abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {
  private var _binding: VB? = null
  protected val binding
    get() = _binding!!

  abstract fun getViewBinding(): VB

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View = getViewBinding().also { _binding = it }.root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setSizeDialog()
  }

  override fun getTheme(): Int = us.wedemy.eggeum.android.design.R.style.EggeumDialog

  @Suppress("NestedBlockDepth")
  private fun setSizeDialog() {
    context?.let {
      dialog?.let { dialog ->
        dialog.window?.let { window ->
          val windowManager: WindowManager =
            activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
          val size = Point()

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val displayMetrics = windowManager.currentWindowMetrics
            size.x = displayMetrics.bounds.width()
            size.y = displayMetrics.bounds.height()
          } else {
            @Suppress("DEPRECATION")
            val display = windowManager.defaultDisplay
            display.getRealSize(size)
          }

          val params: ViewGroup.LayoutParams = window.attributes
          params.width = size.x - 64.dp
          params.height = ViewGroup.LayoutParams.WRAP_CONTENT
          window.setGravity(Gravity.CENTER)
          window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
      }
    }
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}
