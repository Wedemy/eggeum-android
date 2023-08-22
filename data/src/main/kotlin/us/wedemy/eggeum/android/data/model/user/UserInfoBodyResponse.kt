/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import us.wedemy.eggeum.android.domain.model.ProfileImage

@JsonClass(generateAdapter = true)
public data class UserInfoBodyResponse(
  @Json(name = "agreeMarketing")
  public val agreeMarketing: Boolean,

  @Json(name = "email")
  public val email: String,

  @Json(name = "id")
  public val id: Int,

  @Json(name = "name")
  public val name: String,

  @Json(name = "nickname")
  public val nickname: String,

  @Json(name = "profileImage")
  public val profileImage: ProfileImage,

  @Json(name = "roles")
  public val roles: List<String>,

  @Json(name = "status")
  public val status: String,
)
