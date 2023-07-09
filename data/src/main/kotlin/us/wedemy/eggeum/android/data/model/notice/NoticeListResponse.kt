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
