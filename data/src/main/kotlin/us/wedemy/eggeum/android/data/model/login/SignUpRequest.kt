/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// @JsonClass(generateAdapter = true)
// public data class SignUpRequest(
//   @Json(name = "agreemMarketing")
//   public val agreemMarketing: Boolean?,
//
//   @Json(name = "idToken")
//   public val idToken: String,
//
//   @Json(name = "nickname")
//   public val nickname: String,
// )

@Serializable
public data class SignUpRequest(
  @SerialName("agreemMarketing")
  public val agreemMarketing: Boolean?,

  @SerialName("idToken")
  public val idToken: String,

  @SerialName("nickname")
  public val nickname: String,
)
