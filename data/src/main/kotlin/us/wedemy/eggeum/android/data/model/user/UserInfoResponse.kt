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

@Serializable
public data class UserInfoResponse(
  @SerialName("agreeMarketing")
  val agreeMarketing: Boolean,

  @SerialName("email")
  val email: String,

  @SerialName("id")
  val id: Int,

  @SerialName("name")
  val name: String?,

  @SerialName("nickname")
  val nickname: String,

  @SerialName("profileImage")
  val profileImage: ProfileImage?,

  @SerialName("roles")
  val roles: List<String>,

  @SerialName("status")
  val status: String,
)
