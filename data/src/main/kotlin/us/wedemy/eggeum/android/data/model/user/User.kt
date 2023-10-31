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

// @JsonClass(generateAdapter = true)
// public data class User(
//   @Json(name = "agreeMarketing")
//   val agreeMarketing: Boolean,
//
//   @Json(name = "createdBy")
//   val createdBy: Int,
//
//   @Json(name = "createdDate")
//   val createdDate: String,
//
//   @Json(name = "email")
//   val email: String,
//
//   @Json(name = "id")
//   val id: Int,
//
//   @Json(name = "modifiedBy")
//   val modifiedBy: Int,
//
//   @Json(name = "modifiedDate")
//   val modifiedDate: String,
//
//   @Json(name = "name")
//   val name: String,
//
//   @Json(name = "nickname")
//   val nickname: String,
//
//   @Json(name = "password")
//   val password: String,
//
//   @Json(name = "phoneNumber")
//   val phoneNumber: String,
//
//   @Json(name = "profileImage")
//   val profileImage: ProfileImage,
//
//   @Json(name = "roles")
//   val roles: List<String>,
//
//   @Json(name = "snsId")
//   val snsId: String,
//
//   @Json(name = "status")
//   val status: String,
// )

@Serializable
public data class User(
  @SerialName("agreeMarketing")
  val agreeMarketing: Boolean,

  @SerialName("createdBy")
  val createdBy: Int,

  @SerialName("createdDate")
  val createdDate: String,

  @SerialName("email")
  val email: String,

  @SerialName("id")
  val id: Int,

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
