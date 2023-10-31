/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.place

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// @JsonClass(generateAdapter = true)
// public data class Info(
//   @Json(name = "areaSize")
//   val areaSize: String,
//
//   @Json(name = "blogUri")
//   val blogUri: String,
//
//   @Json(name = "businessHours")
//   val businessHours: List<String>,
//
//   @Json(name = "existsSmokingArea")
//   val existsSmokingArea: Boolean,
//
//   @Json(name = "existsWifi")
//   val existsWifi: Boolean,
//
//   @Json(name = "instagramUri")
//   val instagramUri: String,
//
//   @Json(name = "meetingRoomCount")
//   val meetingRoomCount: Int,
//
//   @Json(name = "mobileCharging")
//   val mobileCharging: String,
//
//   @Json(name = "multiSeatCount")
//   val multiSeatCount: Int,
//
//   @Json(name = "parking")
//   val parking: String,
//
//   @Json(name = "phone")
//   val phone: String,
//
//   @Json(name = "restRoom")
//   val restRoom: String,
//
//   @Json(name = "singleSeatCount")
//   val singleSeatCount: Int,
//
//   @Json(name = "websiteUri")
//   val websiteUri: String,
// )

@Serializable
public data class Info(
  @SerialName("areaSize")
  val areaSize: String,

  @SerialName("blogUri")
  val blogUri: String,

  @SerialName("businessHours")
  val businessHours: List<String>,

  @SerialName("existsSmokingArea")
  val existsSmokingArea: Boolean,

  @SerialName("existsWifi")
  val existsWifi: Boolean,

  @SerialName("instagramUri")
  val instagramUri: String,

  @SerialName("meetingRoomCount")
  val meetingRoomCount: Int,

  @SerialName("mobileCharging")
  val mobileCharging: String,

  @SerialName("multiSeatCount")
  val multiSeatCount: Int,

  @SerialName("parking")
  val parking: String,

  @SerialName("phone")
  val phone: String,

  @SerialName("restRoom")
  val restRoom: String,

  @SerialName("singleSeatCount")
  val singleSeatCount: Int,

  @SerialName("websiteUri")
  val websiteUri: String,
)
