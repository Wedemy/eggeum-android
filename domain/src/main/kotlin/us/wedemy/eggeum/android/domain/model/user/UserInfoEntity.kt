/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.user

import us.wedemy.eggeum.android.domain.model.ProfileImageEntity

public data class UserInfoEntity(
  val agreeMarketing: Boolean,
  val email: String,
  val id: Int,
  val name: String?,
  val nickname: String,
  val profileImageEntity: ProfileImageEntity?,
  val roles: List<String>,
  val status: String,
)
