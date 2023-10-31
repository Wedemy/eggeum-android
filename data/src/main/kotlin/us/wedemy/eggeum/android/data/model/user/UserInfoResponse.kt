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
// public data class UserInfoResponse(
//   @Json(name = "agreeMarketing")
//   public val agreeMarketing: Boolean,
//
//   @Json(name = "email")
//   public val email: String,
//
//   @Json(name = "id")
//   public val id: Int,
//
//   @Json(name = "name")
//   public val name: String?,
//
//   @Json(name = "nickname")
//   public val nickname: String,
//
//   @Json(name = "profileImage")
//   public val profileImage: ProfileImageEntity?,
//
//   @Json(name = "roles")
//   public val roles: List<String>,
//
//   @Json(name = "status")
//   public val status: String,
// )

@Serializable
public data class UserInfoResponse(
  @SerialName("agreeMarketing")
  public val agreeMarketing: Boolean,

  @SerialName("email")
  public val email: String,

  @SerialName("id")
  public val id: Int,

  @SerialName("name")
  public val name: String?,

  @SerialName("nickname")
  public val nickname: String,

  @SerialName("profileImage")
  public val profileImage: ProfileImage?,

  @SerialName("roles")
  public val roles: List<String>,

  @SerialName("status")
  public val status: String,
)
