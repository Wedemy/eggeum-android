/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.myaccount

import us.wedemy.eggeum.android.main.model.NoticeModel

sealed class NoticeUiModel {
  object NoticeEmptyItem : NoticeUiModel()

  object NoticeTitleItem : NoticeUiModel()

  object NoticeSearchItem : NoticeUiModel()

  data class NoticeListItem(val notice: NoticeModel) : NoticeUiModel()
}
