/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.cake.util

import android.content.res.Resources
import kotlin.math.roundToInt

public val Int.px: Int
  get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

public val Float.px: Int
  get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()
