/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.place

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// @JsonClass(generateAdapter = true)
// public data class PlaceListResponse(
//   @Json(name = "list")
//   val list: List<PlaceResponse>,
//
//   @Json(name = "totalElements")
//   val totalElements: Int,
//
//   @Json(name = "totalPages")
//   val totalPages: Int,
// )

@Serializable
public data class PlaceListResponse(
  @SerialName("list")
  val list: List<PlaceResponse>,

  @SerialName("totalElements")
  val totalElements: Int,

  @SerialName("totalPages")
  val totalPages: Int,
)
