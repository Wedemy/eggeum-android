/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.util

// "2024-01-07 10:25:28" -> "24.01.07"
fun String.toFormatDate(): String {
  return this.substring(2, 4) + "." + this.substring(5, 7) + "." + this.substring(8, 10)
}

