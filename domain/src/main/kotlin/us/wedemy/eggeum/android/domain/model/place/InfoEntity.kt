/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.place

public data class InfoEntity(
  val areaSize: String,
  val blogUri: String?,
  val businessHours: List<String>?,
  val existsSmokingArea: Boolean?,
  val existsWifi: Boolean,
  val instagramUri: String?,
  val meetingRoomCount: Int,
  val mobileCharging: String?,
  val multiSeatCount: Int?,
  val parking: String?,
  val phone: String?,
  val restRoom: String?,
  val singleSeatCount: Int?,
  val websiteUri: String?,
)
