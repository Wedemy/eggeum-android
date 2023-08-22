/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.report

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class ReportListResponse(
  @Json(name = "list")
  val list: List<ReportBodyResponse>,

  @Json(name = "totalElements")
  val totalElements: Int,

  @Json(name = "totalPages")
  val totalPages: Int,
)
