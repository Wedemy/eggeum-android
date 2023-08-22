/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.mapper

import us.wedemy.eggeum.android.data.model.ProfileImage
import us.wedemy.eggeum.android.data.model.report.ReportBodyResponse
import us.wedemy.eggeum.android.data.model.report.ReportListResponse
import us.wedemy.eggeum.android.data.model.report.User
import us.wedemy.eggeum.android.domain.model.report.ReportBody
import us.wedemy.eggeum.android.domain.model.report.ReportList

internal fun ReportBodyResponse.toDomain() =
  ReportBody(
    content = content,
    status = status,
    title = title,
    user = user.toDomain(),
  )

internal fun ReportListResponse.toDomain() =
  ReportList(
    elements = list.map(ReportBodyResponse::toDomain),
    totalPages = totalPages,
    totalElements = totalElements,
  )

internal fun User.toDomain() =
  us.wedemy.eggeum.android.domain.model.report.User(
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
    profileImage = profileImage.toDomain(),
    roles = roles,
    snsId = snsId,
    status = status,
  )

internal fun ProfileImage.toDomain() =
  us.wedemy.eggeum.android.domain.model.ProfileImage(
    files = files.map { it.toDomain() },
  )
