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
public data class NoticeListResponse(
  @Json(name = "totalPages")
  public val totalPages: Int,

  @Json(name = "list")
  public val list: List<NoticeBodyResponse>,

  @Json(name = "totalElements")
  public val totalElements: Int,
)
