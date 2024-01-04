/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.updatecafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoModel(
  val areaSize: String? = "",
  val blogUri: String? = "",
  val businessHours: List<String>? = emptyList(),
  val existsSmokingArea: Boolean? = null,
  val existsWifi: Boolean? = null,
  val instagramUri: String? = "",
  val meetingRoomCount: Int? = null,
  val mobileCharging: String? = "",
  val multiSeatCount: Int? = null,
  val parking: String? = "",
  val phone: String? = "",
  val restRoom: String? = "",
  val singleSeatCount: Int? = null,
  val websiteUri: String? = "",
) : Parcelable
