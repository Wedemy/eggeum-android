/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.report

import us.wedemy.eggeum.android.domain.model.ProfileImageEntity

public data class UserEntity(
  val agreeMarketing: Boolean,
  val createdBy: Int,
  val createdDate: String,
  val email: String,
  val id: Int,
  val modifiedBy: Int,
  val modifiedDate: String,
  val name: String,
  val nickname: String,
  val password: String,
  val phoneNumber: String,
  val profileImage: ProfileImageEntity,
  val roles: List<String>,
  val snsId: String,
  val status: String,
)
