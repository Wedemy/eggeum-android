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
) {
  public fun updateProposal(
    cafeArea: String,
    cafeMeetingRoom: String,
    cafeMultiSeat: String,
    cafeSingleSeat: String,
    cafeBusinessHours: List<String>,
  ) {
    info.areaSize = cafeArea
    info.meetingRoomCount = cafeMeetingRoom.toInt()
    info.multiSeatCount = cafeMultiSeat.toInt()
    info.singleSeatCount = cafeSingleSeat.toInt()
    info.businessHours = cafeBusinessHours
  }
  public companion object {
    public fun fixture(): UpsertPlaceBody {
      return UpsertPlaceBody(
        address1 = "",
        address2 = "placeBody.address2",
        image = Image.fixture(),
        info = Info.fixture(),
        latitude = 0.0,
        longitude = 0.0,
        menu = Menu.fixture(),
        name = "",
        placeId = 0,
        remarks = "what it is?",
        type = "",
      )
    }
  }
}
