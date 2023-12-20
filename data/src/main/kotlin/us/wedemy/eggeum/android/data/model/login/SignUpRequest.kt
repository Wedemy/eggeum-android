/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class SignUpRequest(
  @SerialName("agreemMarketing")
  val agreemMarketing: Boolean?,

  @SerialName("idToken")
  val idToken: String,

  @SerialName("nickname")
  val nickname: String,
)
