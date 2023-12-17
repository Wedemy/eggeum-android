/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import us.wedemy.eggeum.android.data.model.ProfileImage

// @JsonClass(generateAdapter = true)
// public data class UpdateUserInfoRequest(
//   @Json(name = "nickname")
//   public val nickname: String,
//
//   @Json(name = "profileImage")
//   public val profileImage: ProfileImage,
// )

@Serializable
public data class UpdateUserInfoRequest(
  @SerialName("nickname")
  public val nickname: String,

  @SerialName("profileImage")
  public val profileImage: ProfileImage?,
)
