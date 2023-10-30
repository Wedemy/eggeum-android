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
  val areaSize: String? = null,

  @Json(name = "blogUri")
  val blogUri: String? = null,

  @Json(name = "businessHours")
  val businessHours: List<String?> = emptyList(),

  @Json(name = "existsSmokingArea")
  val existsSmokingArea: Boolean? = null,

  @Json(name = "existsWifi")
  val existsWifi: Boolean? = null,

  @Json(name = "instagramUri")
  val instagramUri: String? = null,

  @Json(name = "meetingRoomCount")
  val meetingRoomCount: Int? = null,

  @Json(name = "mobileCharging")
  val mobileCharging: String? = null,

  @Json(name = "multiSeatCount")
  val multiSeatCount: Int? = null,

  @Json(name = "parking")
  val parking: String? = null,

  @Json(name = "phone")
  val phone: String? = null,

  @Json(name = "restRoom")
  val restRoom: String? = null,

  @Json(name = "singleSeatCount")
  val singleSeatCount: Int? = null,

  @Json(name = "websiteUri")
  val websiteUri: String? = null,
)
