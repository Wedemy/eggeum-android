/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.token

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenResponse(
  @SerialName("accessToken")
  val accessToken: String,

  @SerialName("expiresIn")
  val expiresIn: Long,

  @SerialName("refreshToken")
  val refreshToken: String,

  @SerialName("refreshExpiresIn")
  val refreshExpiresIn: Long,

  @SerialName("userRoles")
  val userRoles: List<String>,
)
