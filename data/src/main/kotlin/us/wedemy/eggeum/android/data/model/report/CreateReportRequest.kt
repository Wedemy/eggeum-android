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
// public data class CreateReportRequest(
//   @Json(name = "title")
//   val title: String,
//
//   @Json(name = "content")
//   val content: String,
// )

@Serializable
public data class CreateReportRequest(
  @SerialName("title")
  val title: String,

  @SerialName("content")
  val content: String,
)
