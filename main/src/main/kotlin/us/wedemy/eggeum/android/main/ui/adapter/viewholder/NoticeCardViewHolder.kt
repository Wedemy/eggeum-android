/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import us.wedemy.eggeum.android.common.base.BaseViewHolder
import us.wedemy.eggeum.android.main.databinding.ItemNoticeCardBinding
import us.wedemy.eggeum.android.main.model.NoticeCardModel
import us.wedemy.eggeum.android.main.ui.adapter.listener.NoticeCardClickListener

class NoticeCardViewHolder(
  binding: ItemNoticeCardBinding,
  clickListener: NoticeCardClickListener,
) : BaseViewHolder<NoticeCardModel, ItemNoticeCardBinding>(binding) {

  init {
    binding.root.setOnClickListener {
      clickListener.onItemClick(adapterPosition)
    }
  }

  override fun bind(item: NoticeCardModel) {
    binding.apply {
      tvHomeNoticeTitle.text = item.title
      tvNotificationCreatedDate.text = item.date
    }
  }
}
