/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.notice

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class NoticeResponse(
  @SerialName("id")
  val id: Int,

  @SerialName("viewCount")
  val viewCount: Int,

  @SerialName("title")
  val title: String,

  @SerialName("content")
  val content: String,

  @SerialName("createdDate")
  val createdDate: String,

  @SerialName("modifiedDate")
  val modifiedDate: String,
)
