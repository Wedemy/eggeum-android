/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoModel(
  val areaSize: String? = "",
  val blogUri: String? = "",
  val businessHours: List<String>? = emptyList(),
  val existsSmokingArea: Boolean? = false,
  val existsWifi: Boolean? = false,
  val instagramUri: String? = "",
  val meetingRoomCount: Int? = -1,
  val mobileCharging: String? = "",
  val multiSeatCount: Int? = -1,
  val parking: String? = "",
  val phone: String? = "",
  val restRoom: String? = "",
  val singleSeatCount: Int? = -1,
  val websiteUri: String? = "",
): Parcelable
