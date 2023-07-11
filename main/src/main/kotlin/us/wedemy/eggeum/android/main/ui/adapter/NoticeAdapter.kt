/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import us.wedemy.eggeum.android.main.databinding.ItemNoticeBinding
import us.wedemy.eggeum.common.extension.layoutInflater
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NoticeViewHolder
import us.wedemy.eggeum.android.main.ui.item.NoticeItem

class NoticeAdapter(private val clickListener: (Int) -> Unit) :
  ListAdapter<NoticeItem, NoticeViewHolder>(NoticeItemDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    NoticeViewHolder(ItemNoticeBinding.inflate(parent.context.layoutInflater, parent, false))

  override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
    val noticeItem = getItem(position)

    with(holder) {
      noticeItem?.let { notice ->
        bind(notice)
        binding.root.setOnClickListener {
          clickListener(adapterPosition)
        }
      } ?: binding.root.setOnClickListener(null)
    }
  }

  private companion object {
    private val NoticeItemDiffCallback = object : DiffUtil.ItemCallback<NoticeItem>() {
      override fun areItemsTheSame(oldItem: NoticeItem, newItem: NoticeItem) =
        oldItem.noticeTitle == newItem.noticeTitle

      override fun areContentsTheSame(oldItem: NoticeItem, newItem: NoticeItem) = oldItem == newItem
    }
  }
}
