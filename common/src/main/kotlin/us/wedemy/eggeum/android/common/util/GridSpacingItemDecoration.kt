/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
  private val spanCount: Int, // Grid의 column 수
  private val spacing: Int // 간격
) : RecyclerView.ItemDecoration() {

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {
    val position: Int = parent.getChildAdapterPosition(view)

    if (position >= 0) {
      val column = position % spanCount // item column
      outRect.apply {
        // spacing - column * ((1f / spanCount) * spacing)
        left = spacing - column * spacing / spanCount
        // (column + 1) * ((1f / spanCount) * spacing)
        right = (column + 1) * spacing / spanCount
        if (position < spanCount) top = spacing
        bottom = spacing
      }
    } else {
      outRect.apply {
        left = 0
        right = 0
        top = 0
        bottom = 0
      }
    }
  }
}