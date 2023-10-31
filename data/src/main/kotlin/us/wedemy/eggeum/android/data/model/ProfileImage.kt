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
// public data class ProfileImage(
//   @Json(name = "files")
//   public val files: List<File>,
// )

@Serializable
public data class ProfileImage(
  @SerialName("files")
  public val files: List<File>,
)
