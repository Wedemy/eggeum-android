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
// public data class LoginResponse(
//   @Json(name = "accessToken")
//   public val accessToken: String,
//
//   @Json(name = "expiresIn")
//  public val expiresIn: Long,
//
//   @Json(name = "refreshToken")
//  public val refreshToken: String,
//
//   @Json(name = "refreshExpiresIn")
//   public val refreshExpiresIn: Long,
//
//   @Json(name = "userRoles")
//   public val userRoles: List<String>,
// )

@Serializable
public data class LoginResponse(
  @SerialName("accessToken")
  public val accessToken: String,

  @SerialName("expiresIn")
  public val expiresIn: Long,

  @SerialName("refreshToken")
  public val refreshToken: String,

  @SerialName("refreshExpiresIn")
  public val refreshExpiresIn: Long,

  @SerialName("userRoles")
  public val userRoles: List<String>,
)
