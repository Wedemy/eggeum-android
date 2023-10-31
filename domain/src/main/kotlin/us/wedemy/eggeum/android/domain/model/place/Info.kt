/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.place

public data class Info(
  var areaSize: String?,
  var blogUri: String?,
  var businessHours: List<String?>,
  var existsSmokingArea: Boolean?,
  var existsWifi: Boolean?,
  var instagramUri: String?,
  var meetingRoomCount: Int?,
  var mobileCharging: String?,
  var multiSeatCount: Int?,
  var parking: String?,
  var phone: String?,
  var restRoom: String?,
  var singleSeatCount: Int?,
  var websiteUri: String?,
) {
  public fun setProposeBody(
      cafeArea: String,
      cafeMeetingRoom: String,
      cafeMultiSeat: String,
      cafeSingleSeat: String,
      cafeBusinessHours: String,
      cafeParking: String,
      cafeExistsSmokingArea: String,
      cafeExistsWifi: String,
      cafeRestRoom: String,
      cafeMobileCharging: String,
      cafeInstagramUri: String,
      cafeWebsiteUri: String,
      cafeBlogUri: String,
      cafePhone: String,
  ) {
    areaSize = cafeArea
    meetingRoomCount = cafeMeetingRoom.toInt()
    multiSeatCount = cafeMultiSeat.toInt()
    singleSeatCount = cafeSingleSeat.toInt()
    businessHours = cafeBusinessHours.split(" ~ ")
    parking = cafeParking
    existsSmokingArea = cafeExistsSmokingArea != "X" // false면 무조건 X 표시
    existsWifi = cafeExistsWifi != "X"
    restRoom = cafeRestRoom
    mobileCharging = cafeMobileCharging
    instagramUri = cafeInstagramUri
    websiteUri = cafeWebsiteUri
    blogUri = cafeBlogUri
    phone = cafePhone
  }

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
