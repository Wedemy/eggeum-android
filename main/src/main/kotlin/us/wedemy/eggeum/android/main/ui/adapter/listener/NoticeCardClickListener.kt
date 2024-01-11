/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.listener

import us.wedemy.eggeum.android.main.model.NoticeCardModel

interface NoticeCardClickListener {
  fun onItemClick(noticeItem: NoticeCardModel)
}
