/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.login

public data class SignUpRequestEntity(
  val agreemMarketing: Boolean?,
  val idToken: String,
  val nickname: String,
)
