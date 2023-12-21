/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.extension

import android.content.res.Resources
import kotlin.math.roundToInt

val Int.dp: Int
  get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()