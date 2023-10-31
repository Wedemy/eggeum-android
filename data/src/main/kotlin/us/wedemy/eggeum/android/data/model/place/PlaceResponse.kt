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
// public data class PlaceResponse(
//   @Json(name = "address1")
//   val address1: String,
//
//   @Json(name = "address2")
//   val address2: String,
//
//   @Json(name = "id")
//   val id: Int,
//
//   @Json(name = "image")
//   val image: Image,
//
//   @Json(name = "info")
//   val info: Info,
//
//   @Json(name = "latitude")
//   val latitude: Double,
//
//   @Json(name = "longitude")
//   val longitude: Double,
//
//   @Json(name = "menu")
//   val menu: Menu,
//
//   @Json(name = "name")
//   val name: String,
//
//   @Json(name = "type")
//   val type: String,
// )

@Serializable
public data class PlaceResponse(
  @SerialName("address1")
  val address1: String,

  @SerialName("address2")
  val address2: String,

  @SerialName("id")
  val id: Int,

  @SerialName("image")
  val image: Image,

  @SerialName("info")
  val info: Info,

  @SerialName("latitude")
  val latitude: Double,

  @SerialName("longitude")
  val longitude: Double,

  @SerialName("menu")
  val menu: Menu,

  @SerialName("name")
  val name: String,

  @SerialName("type")
  val type: String,
)
