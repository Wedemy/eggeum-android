/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.mapper

import us.wedemy.eggeum.android.data.model.File
import us.wedemy.eggeum.android.data.model.ProfileImage
import us.wedemy.eggeum.android.data.model.user.UserInfoBodyResponse
import us.wedemy.eggeum.android.domain.model.user.UserInfoBody

internal fun UserInfoBodyResponse.toDomain() =
  UserInfoBody(
    agreeMarketing = agreeMarketing,
    email = email,
    id = id,
    name = name,
    nickname = nickname,
    profileImage = profileImage,
    roles = roles,
    status = status,
  )

internal fun us.wedemy.eggeum.android.domain.model.ProfileImage.toData() =
  ProfileImage(
    files = files.map { it.toData() },
  )

internal fun us.wedemy.eggeum.android.domain.model.File.toData() =
  File(
    uploadFileId = uploadFileId,
    url = url,
  )
