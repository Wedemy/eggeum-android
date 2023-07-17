/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.util

import android.graphics.drawable.ColorDrawable
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addDivider(@ColorRes colorRes: Int, orientation: Int = DividerItemDecoration.VERTICAL) {
  val colorDrawable = ColorDrawable(ContextCompat.getColor(this.context, colorRes))
  val dividerItemDecoration = DividerItemDecoration(this.context, orientation)
  dividerItemDecoration.setDrawable(colorDrawable)
  this.addItemDecoration(dividerItemDecoration)
}
