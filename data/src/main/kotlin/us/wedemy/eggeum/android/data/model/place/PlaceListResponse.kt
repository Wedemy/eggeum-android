/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.place

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PlaceListResponse(
  @SerialName("list")
  val list: List<PlaceResponse>,

  @SerialName("totalElements")
  val totalElements: Int,

  @SerialName("totalPages")
  val totalPages: Int,
)
