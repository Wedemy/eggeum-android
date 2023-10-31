/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.mapper

import us.wedemy.eggeum.android.data.model.notice.NoticeResponse
import us.wedemy.eggeum.android.data.model.notice.NoticeListResponse
import us.wedemy.eggeum.android.domain.model.notice.NoticeEntity
import us.wedemy.eggeum.android.domain.model.notice.NoticeListEntity

internal fun NoticeResponse.toEntity() =
  NoticeEntity(
    id = id,
    title = title,
    content = content,
    viewCount = viewCount,
  )

internal fun NoticeListResponse.toEntity() =
  NoticeListEntity(
    elements = list.map(NoticeResponse::toEntity),
    totalPages = totalPages,
    totalElements = totalElements,
  )
