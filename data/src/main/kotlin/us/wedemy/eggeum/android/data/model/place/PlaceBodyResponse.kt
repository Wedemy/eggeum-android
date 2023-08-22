/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.place

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class PlaceBodyResponse(
  @Json(name = "address1")
  val address1: String,

  @Json(name = "address2")
  val address2: String,

  @Json(name = "id")
  val id: Int,

  @Json(name = "image")
  val image: Image,

  @Json(name = "info")
  val info: Info,

  @Json(name = "latitude")
  val latitude: Double,

  @Json(name = "longitude")
  val longitude: Double,

  @Json(name = "menu")
  val menu: Menu,

  @Json(name = "name")
  val name: String,

  @Json(name = "type")
  val type: String,
)
