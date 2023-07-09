/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.notice

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class NoticeBodyResponse(
  @Json(name = "id")
  public val id: Int,

  @Json(name = "viewCount")
  public val viewCount: Int,

  @Json(name = "title")
  public val title: String,

  @Json(name = "content")
  public val content: String,
)
