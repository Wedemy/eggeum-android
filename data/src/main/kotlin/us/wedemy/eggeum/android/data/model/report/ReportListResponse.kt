/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// @JsonClass(generateAdapter = true)
// public data class ReportListResponse(
//   @Json(name = "list")
//   val list: List<ReportResponse>,
//
//   @Json(name = "totalElements")
//   val totalElements: Int,
//
//   @Json(name = "totalPages")
//   val totalPages: Int,
// )

@Serializable
public data class ReportListResponse(
  @SerialName("list")
  val list: List<ReportResponse>,

  @SerialName("totalElements")
  val totalElements: Int,

  @SerialName("totalPages")
  val totalPages: Int,
)
