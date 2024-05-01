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
internal data class User(
  @SerialName("agreeMarketing")
  val agreeMarketing: Boolean,

  @SerialName("createdBy")
  val createdBy: Int,

  @SerialName("createdDate")
  val createdDate: String,

  @SerialName("email")
  val email: String,

  @SerialName("id")
  val id: Long,

  @SerialName("modifiedBy")
  val modifiedBy: Int,

  @SerialName("modifiedDate")
  val modifiedDate: String,

  @SerialName("name")
  val name: String,

  @SerialName("nickname")
  val nickname: String,

  @SerialName("password")
  val password: String,

  @SerialName("phoneNumber")
  val phoneNumber: String,

  @SerialName("profileImage")
  val profileImage: ProfileImage,

  @SerialName("roles")
  val roles: List<String>,

  @SerialName("snsId")
  val snsId: String,

  @SerialName("status")
  val status: String,
)
