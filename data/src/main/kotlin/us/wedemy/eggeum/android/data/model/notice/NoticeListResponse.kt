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
// public data class NoticeListResponse(
//   @Json(name = "list")
//   public val list: List<NoticeResponse>,
//
//   @Json(name = "totalElements")
//   public val totalElements: Int,
//
//   @Json(name = "totalPages")
//   public val totalPages: Int,
// )

@Serializable
public data class NoticeListResponse(
  @SerialName("list")
  public val list: List<NoticeResponse>,

  @SerialName("totalElements")
  public val totalElements: Int,

  @SerialName("totalPages")
  public val totalPages: Int,
)
