/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.mapper

import us.wedemy.eggeum.android.data.model.notice.NoticeBodyResponse
import us.wedemy.eggeum.android.data.model.notice.NoticeListResponse
import us.wedemy.eggeum.domain.model.notice.NoticeBody
import us.wedemy.eggeum.domain.model.notice.NoticeList

internal fun NoticeBodyResponse.toDomain() =
  NoticeBody(
    id = id,
    title = title,
    content = content,
    viewCount = viewCount,
  )

internal fun NoticeListResponse.toDomain() =
  NoticeList(
    elements = list.map(NoticeBodyResponse::toDomain),
    totalPages = totalPages,
    totalElements = totalElements,
  )
