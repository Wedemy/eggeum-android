/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.main.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import us.wedemy.eggeum.main.databinding.ItemNoticeBinding
import us.wedemy.eggeum.main.ui.item.NoticeItem

class NoticeViewHolder(val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(noticeItem: NoticeItem) {
    binding.tvHomeNoticeTitle.text = noticeItem.noticeTitle
    binding.tvNotificationDate.text = noticeItem.noticeDate
  }
}
