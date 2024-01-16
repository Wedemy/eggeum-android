/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.mapper

import us.wedemy.eggeum.android.common.util.toFormatDate
import us.wedemy.eggeum.android.domain.model.notice.NoticeEntity
import us.wedemy.eggeum.android.main.model.NoticeCardModel
import us.wedemy.eggeum.android.main.model.NoticeModel

internal fun NoticeEntity.toNoticeCardModel() =
  NoticeCardModel(
    id = id,
    title = title,
    date = createdDate.toFormatDate(),
  )

internal fun NoticeEntity.toNoticModel() =
  NoticeModel(
    id = id,
    title = title,
    date = createdDate.toFormatDate(),
    description = content,
    isExpanded = false,
  )