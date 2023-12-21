/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.extension

import android.content.res.Resources

fun Float.fromDpToPx(): Int =
  (this * Resources.getSystem().displayMetrics.density).toInt()
