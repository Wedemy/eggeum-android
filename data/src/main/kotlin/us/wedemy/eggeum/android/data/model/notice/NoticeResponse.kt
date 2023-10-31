/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.notice

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// @JsonClass(generateAdapter = true)
// public data class NoticeResponse(
//   @Json(name = "id")
//   public val id: Int,
//
//   @Json(name = "viewCount")
//   public val viewCount: Int,
//
//   @Json(name = "title")
//   public val title: String,
//
//   @Json(name = "content")
//   public val content: String,
// )

@Serializable
public data class NoticeResponse(
  @SerialName("id")
  public val id: Int,

  @SerialName("viewCount")
  public val viewCount: Int,

  @SerialName("title")
  public val title: String,

  @SerialName("content")
  public val content: String,
)
