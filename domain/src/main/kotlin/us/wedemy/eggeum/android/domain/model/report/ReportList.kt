/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.report

public data class ReportList(
  val elements: List<ReportBody>,
  val totalElements: Int,
  val totalPages: Int,
)
