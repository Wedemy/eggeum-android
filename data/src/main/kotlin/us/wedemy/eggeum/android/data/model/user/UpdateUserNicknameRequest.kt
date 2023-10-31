/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// @JsonClass(generateAdapter = true)
// public data class UpdateUserNicknameRequest(
//   @Json(name = "nickname")
//   public val nickname: String,
// )

@Serializable
public data class UpdateUserNicknameRequest(
  @SerialName("nickname")
  public val nickname: String,
)
