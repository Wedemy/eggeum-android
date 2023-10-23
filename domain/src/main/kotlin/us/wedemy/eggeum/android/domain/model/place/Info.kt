/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.place

public data class Info(
  var areaSize: String,
  val blogUri: String,
  var businessHours: List<String>,
  val existsSmokingArea: Boolean,
  val existsWifi: Boolean,
  val instagramUri: String,
  var meetingRoomCount: Int,
  val mobileCharging: String,
  var multiSeatCount: Int,
  val parking: String,
  val phone: String,
  val restRoom: String,
  var singleSeatCount: Int,
  val websiteUri: String,
) {
  public companion object {
    public fun fixture(): Info {
      return Info(
        areaSize = "",
        blogUri = "",
        businessHours = emptyList(),
        existsSmokingArea = false,
        existsWifi = false,
        instagramUri = "",
        meetingRoomCount = 0,
        mobileCharging = "",
        multiSeatCount = 0,
        parking = "",
        phone = "",
        restRoom = "",
        singleSeatCount = 0,
        websiteUri = "",
      )
    }
  }
}
