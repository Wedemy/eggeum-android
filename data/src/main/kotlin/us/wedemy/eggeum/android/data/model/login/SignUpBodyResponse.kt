/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
public data class SignUpBodyResponse(
  @Json(name = "accessToken")
  public val accessToken: String,

  @Json(name = "expiresIn")
  public val expiresIn: Int,

  @Json(name = "refreshToken")
  public val refreshToken: String,

  @Json(name = "refreshExpiresIn")
  public val refreshExpiresIn: Int,

  @Json(name = "userRoles")
  public val userRoles: List<String>,
)
