
/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.updatecafe.ui.item

import us.wedemy.eggeum.android.domain.model.place.InfoEntity

data class CafeInfoItem(
  var areaSize: String? = null,
  var blogUri: String? = null,
  var businessHours: List<String>? = null,
  var existsSmokingArea: Boolean? = null,
  var existsWifi: Boolean? = null,
  var existsOutlet: Boolean? = null,
  var instagramUri: String? = null,
  var meetingRoomCount: String? = null,
  var mobileCharging: String? = null,
  var multiSeatCount: String? = null,
  var parking: String? = null,
  var phone: String? = null,
  var restRoom: String? = null,
  var singleSeatCount: String? = null,
  var websiteUri: String? = null,
) {
  fun toEntity(): InfoEntity {
    return InfoEntity(
      areaSize = areaSize,
      blogUri = blogUri,
      businessHours = businessHours,
      existsSmokingArea = existsSmokingArea,
      existsWifi = existsWifi,
      existsOutlet = existsOutlet,
      instagramUri = instagramUri,
      meetingRoomCount = meetingRoomCount?.toInt(),
      mobileCharging = mobileCharging,
      multiSeatCount = multiSeatCount?.toInt(),
      parking = parking,
      phone = phone,
      restRoom = restRoom,
      singleSeatCount = singleSeatCount?.toInt(),
      websiteUri = websiteUri,
    )
  }
}
