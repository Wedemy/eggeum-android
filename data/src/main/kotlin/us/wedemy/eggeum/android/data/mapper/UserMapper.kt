/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.mapper

import us.wedemy.eggeum.android.data.model.user.User
import us.wedemy.eggeum.android.data.model.user.UpdateUserInfoRequest
import us.wedemy.eggeum.android.data.model.user.UpdateUserNicknameRequest
import us.wedemy.eggeum.android.data.model.user.UserInfoResponse
import us.wedemy.eggeum.android.domain.model.report.UserEntity
import us.wedemy.eggeum.android.domain.model.user.UpdateUserInfoEntity
import us.wedemy.eggeum.android.domain.model.user.UpdateUserNicknameEntity
import us.wedemy.eggeum.android.domain.model.user.UserInfoEntity

internal fun UserInfoResponse.toEntity() =
  UserInfoEntity(
    agreeMarketing = agreeMarketing,
    email = email,
    id = id,
    name = name,
    nickname = nickname,
    profileImageEntity = profileImage?.toEntity(),
    roles = roles,
    status = status,
  )

internal fun UpdateUserInfoEntity.toModel() =
  UpdateUserInfoRequest(
    nickname = nickname,
    profileImage = profileImageEntity?.toModel(),
  )

internal fun UpdateUserNicknameEntity.toModel() =
  UpdateUserNicknameRequest(
    nickname = nickname,
  )

internal fun User.toEntity() =
  UserEntity(
    agreeMarketing = agreeMarketing,
    createdBy = createdBy,
    createdDate = createdDate,
    email = email,
    id = id,
    modifiedBy = modifiedBy,
    modifiedDate = modifiedDate,
    name = name,
    nickname = nickname,
    password = password,
    phoneNumber = phoneNumber,
    profileImage = profileImage.toEntity(),
    roles = roles,
    snsId = snsId,
    status = status,
  )
