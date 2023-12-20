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
public data class NoticeListResponse(
  @SerialName("list")
  val list: List<NoticeResponse>,

  @SerialName("totalElements")
  val totalElements: Int,

  @SerialName("totalPages")
  val totalPages: Int,
)
