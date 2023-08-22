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
public data class Info(
  @Json(name = "areaSize")
  val areaSize: String,

  @Json(name = "blogUri")
  val blogUri: String,

  @Json(name = "businessHours")
  val businessHours: List<String>,

  @Json(name = "existsSmokingArea")
  val existsSmokingArea: Boolean,

  @Json(name = "existsWifi")
  val existsWifi: Boolean,

  @Json(name = "instagramUri")
  val instagramUri: String,

  @Json(name = "meetingRoomCount")
  val meetingRoomCount: Int,

  @Json(name = "mobileCharging")
  val mobileCharging: String,

  @Json(name = "multiSeatCount")
  val multiSeatCount: Int,

  @Json(name = "parking")
  val parking: String,

  @Json(name = "phone")
  val phone: String,

  @Json(name = "restRoom")
  val restRoom: String,

  @Json(name = "singleSeatCount")
  val singleSeatCount: Int,

  @Json(name = "websiteUri")
  val websiteUri: String,
)
