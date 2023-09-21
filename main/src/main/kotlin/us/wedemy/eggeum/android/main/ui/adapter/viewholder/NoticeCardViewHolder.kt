/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import us.wedemy.eggeum.android.main.databinding.ItemNoticeCardBinding
import us.wedemy.eggeum.android.main.ui.item.NoticeCardItem

class NoticeCardViewHolder(val binding: ItemNoticeCardBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(notice: NoticeCardItem) {
    binding.apply {
      tvHomeNoticeTitle.text = notice.title
      tvNotificationDate.text = notice.date
    }
  }
}
