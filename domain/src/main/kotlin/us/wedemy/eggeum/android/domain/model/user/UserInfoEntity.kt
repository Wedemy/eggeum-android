/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.user

import us.wedemy.eggeum.android.domain.model.ProfileImageEntity

public data class UserInfoEntity(
  public val agreeMarketing: Boolean,
  public val email: String,
  public val id: Int,
  public val name: String?,
  public val nickname: String,
  public val profileImageEntity: ProfileImageEntity?,
  public val roles: List<String>,
  public val status: String,
)
