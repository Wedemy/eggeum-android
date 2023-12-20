/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.place

public data class UpsertPlaceEntity(
  val address1: String?,
  val address2: String?,
  val image: ImageEntity?,
  val info: InfoEntity,
  val latitude: Double?,
  val longitude: Double?,
  val menu: MenuEntity?,
  val name: String?,
  val placeId: Int?,
  val remarks: String?,
  val type: String?,
) {
  public companion object {
    public fun of(info: InfoEntity, placeId: Int, name: String?): UpsertPlaceEntity {
      return UpsertPlaceEntity(
        address1 = null,
        address2 = null,
        image = null,
        info = info,
        latitude = null,
        longitude = null,
        menu = null,
        name = name,
        placeId = placeId,
        remarks = null,
        type = null,
      )
    }
  }
}
