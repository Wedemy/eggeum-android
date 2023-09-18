/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.ui

import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

abstract class BaseBottomSheetFragment<VB : ViewBinding> : BottomSheetDialogFragment() {
  private var _binding: VB? = null
  protected val binding: VB
    get() = _binding!!

  abstract fun getViewBinding(): VB

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ) = getViewBinding().also { _binding = it }.root

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog: Dialog = super.onCreateDialog(savedInstanceState)
    if (dialog is BottomSheetDialog) {
      dialog.setOnShowListener {
        val bottomSheetView = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheetView?.post {
          dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
          val newMaterialShapeDrawable = createMaterialShapeDrawable(bottomSheetView)
          ViewCompat.setBackground(bottomSheetView, newMaterialShapeDrawable)
        }
      }
    }
    return dialog
  }

  @ColorRes
  protected open val backgroundColor: Int? = null

  private fun createMaterialShapeDrawable(bottomSheet: View): MaterialShapeDrawable {
    val shapeAppearanceModel = ShapeAppearanceModel.builder(
      context,
      0,
      com.google.android.material.R.style.ShapeAppearance_MaterialComponents_SmallComponent
    ).build()

    val currentMaterialShapeDrawable = bottomSheet.background as? MaterialShapeDrawable ?: MaterialShapeDrawable()
    val newMaterialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
    newMaterialShapeDrawable.apply {
      initializeElevationOverlay(context)
      fillColor = backgroundColor?.let { ColorStateList.valueOf(ContextCompat.getColor(requireContext(), it)) }
        ?: currentMaterialShapeDrawable.fillColor
      tintList = currentMaterialShapeDrawable.tintList
      elevation = currentMaterialShapeDrawable.elevation
      strokeWidth = currentMaterialShapeDrawable.strokeWidth
      strokeColor = currentMaterialShapeDrawable.strokeColor
    }
    return newMaterialShapeDrawable
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }
}
