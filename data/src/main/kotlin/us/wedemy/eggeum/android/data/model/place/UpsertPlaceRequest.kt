/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.place

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// JsonClass(generateAdapter = true)
// public data class UpsertPlaceRequest(
//   @Json(name = "address1")
//   val address1: String,
//
//   @Json(name = "address2")
//   val address2: String,
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
//   @Json(name = "placeId")
//   val placeId: Int,
//
//   @Json(name = "remarks")
//   val remarks: String,
//
//   @Json(name = "type")
//   val type: String,
// )

@Serializable
public data class UpsertPlaceRequest(
  @SerialName("address1")
  val address1: String?,

  @SerialName("address2")
  val address2: String?,

  @SerialName("image")
  val image: Image?,

  @SerialName("info")
  val info: Info,

  @SerialName("latitude")
  val latitude: Double?,

  @SerialName("longitude")
  val longitude: Double?,

  @SerialName("menu")
  val menu: Menu?,

  @SerialName("name")
  val name: String?,

  @SerialName("placeId")
  val placeId: Int,

  @SerialName("remarks")
  val remarks: String?,

  @SerialName("type")
  val type: String?,
)
