/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import us.wedemy.eggeum.android.data.model.ProfileImage

@JsonClass(generateAdapter = true)
public data class UpdateUserInfoBodyRequest(
  @Json(name = "nickname")
  public val nickname: String,

  @Json(name = "profileImage")
  public val profileImage: ProfileImage,
)
