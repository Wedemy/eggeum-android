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

internal fun NoticeEntity.toUiModel() =
  NoticeCardModel(
    id = id,
    title = title,
    date = createdDate.toFormatDate(),
  )
