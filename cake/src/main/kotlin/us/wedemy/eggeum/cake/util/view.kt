/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.cake.util

import android.view.View

public fun View.setHorizontalPadding(padding: Int) {
  setPadding(
    /* left = */ padding,
    /* top = */ paddingTop,
    /* right = */ padding,
    /* bottom = */ paddingBottom,
  )
}
