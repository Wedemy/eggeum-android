/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// @JsonClass(generateAdapter = true)
// public data class File(
//   @Json(name = "uploadFileId")
//   val uploadFileId: Int,
//
//   @Json(name = "url")
//   val url: String,
// )

@Serializable
public data class File(
  @SerialName("uploadFileId")
  val uploadFileId: Int,

  @SerialName("url")
  val url: String?,
)
