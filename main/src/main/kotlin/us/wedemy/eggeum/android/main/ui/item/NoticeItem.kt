/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.item

data class NoticeItem(
  val title: String,
  val date: String,
  val description: String,
  var isExpanded: Boolean = false,
)
