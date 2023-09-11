/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import us.wedemy.eggeum.android.main.databinding.ItemNoticeBinding
import us.wedemy.eggeum.android.main.ui.item.NoticeItem

class NoticeViewHolder(val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(notice: NoticeItem) {
    "[공지] ${notice.title}".also { binding.tvNoticeTitle.text = it }
    // binding.tvNoticeContent.text = notice.content
  }
}
