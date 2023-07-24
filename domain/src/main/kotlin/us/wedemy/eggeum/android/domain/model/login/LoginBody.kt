/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.login

public data class LoginBody(
  val accessToken: String,
  val expiresIn: Int,
  val refreshToken: String,
  val refreshExpiresIn: Int,
  val userRoles: List<String>,
)
