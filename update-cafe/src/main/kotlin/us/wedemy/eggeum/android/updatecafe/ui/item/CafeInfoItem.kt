
/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.updatecafe.ui.item

import us.wedemy.eggeum.android.domain.model.place.InfoEntity

data class CafeInfoItem(
  val areaSize: String? = null,
  val blogUri: String? = null,
  val businessHours: List<String>? = null,
  val existsSmokingArea: Boolean? = null,
  val existsWifi: Boolean? = null,
  val instagramUri: String? = null,
  val meetingRoomCount: Int? = null,
  val mobileCharging: String? = null,
  val multiSeatCount: Int? = null,
  val parking: String? = null,
  val phone: String? = null,
  val restRoom: String? = null,
  val singleSeatCount: Int? = null,
  val websiteUri: String? = null,
) {
  fun toEntity(): InfoEntity {
    return InfoEntity(
      areaSize = areaSize,
      blogUri = blogUri,
      businessHours = businessHours,
      existsSmokingArea = existsSmokingArea,
      existsWifi = existsWifi,
      instagramUri = instagramUri,
      meetingRoomCount = meetingRoomCount,
      mobileCharging = mobileCharging,
      multiSeatCount = multiSeatCount,
      parking = parking,
      phone = phone,
      restRoom = restRoom,
      singleSeatCount = singleSeatCount,
      websiteUri = websiteUri,
    )
  }
}
