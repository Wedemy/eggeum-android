/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import us.wedemy.eggeum.android.data.model.user.User

// @JsonClass(generateAdapter = true)
// public data class ReportResponse(
//   @Json(name = "content")
//   val content: String,
//
//   @Json(name = "status")
//   val status: String,
//
//   @Json(name = "title")
//   val title: String,
//
//   @Json(name = "user")
//   val user: User,
// )

@Serializable
public data class ReportResponse(
  @SerialName("content")
  val content: String,

  @SerialName("status")
  val status: String,

  @SerialName("title")
  val title: String,

  @SerialName("user")
  val user: User,
)
