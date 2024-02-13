/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.place

public data class PlaceEntity(
  val address1: String?,
  val address2: String?,
  val id: Long,
  val image: ImageEntity?,
  var info: InfoEntity?,
  val latitude: Double?,
  val longitude: Double?,
  val menu: MenuEntity?,
  val name: String,
  val type: String?,
) {
  public fun toUpsertPlaceEntity(): UpsertPlaceEntity {
    return UpsertPlaceEntity(
      address1 = address1,
      address2 = address2,
      placeId = id,
      image = image,
      info = info!!,
      latitude = latitude,
      longitude = longitude,
      menu = menu,
      name = name,
      remarks = null,
      type = type,
    )
  }
}
