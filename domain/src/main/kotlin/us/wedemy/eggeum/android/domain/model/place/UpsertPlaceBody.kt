/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.place

public data class UpsertPlaceBody(
  val address1: String,
  val address2: String,
  val image: Image,
  val info: Info,
  val latitude: Double,
  val longitude: Double,
  val menu: Menu,
  val name: String,
  val placeId: Int,
  val remarks: String,
  val type: String,
)
