/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import us.wedemy.eggeum.android.main.databinding.ItemNoticeCardBinding
import us.wedemy.eggeum.android.main.model.NoticeCardModel
import us.wedemy.eggeum.android.main.ui.adapter.listener.NoticeCardClickListener
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NoticeCardViewHolder

class NoticeCardAdapter(
  private val clickListener: NoticeCardClickListener,
) : PagingDataAdapter<NoticeCardModel, NoticeCardViewHolder>(NoticeCardModelDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    NoticeCardViewHolder(
      ItemNoticeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )

  override fun onBindViewHolder(holder: NoticeCardViewHolder, position: Int) {
    val noticeItem = getItem(position)
    noticeItem?.let { notice ->
      holder.bind(notice)
      holder.binding.root.setOnClickListener {
        clickListener.onItemClick(noticeItem)
      }
    }
  }

  companion object {
    private val NoticeCardModelDiffCallback = object : DiffUtil.ItemCallback<NoticeCardModel>() {
      override fun areItemsTheSame(
        oldItem: NoticeCardModel,
        newItem: NoticeCardModel,
      ): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
        oldItem: NoticeCardModel,
        newItem: NoticeCardModel,
      ): Boolean {
        return oldItem == newItem
      }
    }
  }
}
