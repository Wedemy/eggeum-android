/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import us.wedemy.eggeum.android.main.databinding.ItemNoticeListBinding
import us.wedemy.eggeum.android.main.model.NoticeModel
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NoticeListItemViewHolder

class NoticeAdapter : PagingDataAdapter<NoticeModel, NoticeListItemViewHolder>(NoticeItemDiffCallback) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    NoticeListItemViewHolder(
      ItemNoticeListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )

  override fun onBindViewHolder(holder: NoticeListItemViewHolder, position: Int) {
    val noticeItem = getItem(position)
    noticeItem?.let { notice ->
      holder.bind(notice)
      holder.binding.clNotice.setOnClickListener {
        noticeItem.isExpanded = !noticeItem.isExpanded
        toggleLayout(noticeItem.isExpanded, holder.binding.ivNoticeExpand)
        notifyItemChanged(position)
      }
    }
  }

  private fun toggleLayout(isExpanded: Boolean, view: View) {
    if (isExpanded) {
      view.animate().setDuration(200).rotation(180f)
    } else {
      view.animate().setDuration(200).rotation(0f)
    }
  }

  companion object {
    private val NoticeItemDiffCallback = object : DiffUtil.ItemCallback<NoticeModel>() {
      override fun areItemsTheSame(
        oldItem: NoticeModel,
        newItem: NoticeModel,
      ): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
        oldItem: NoticeModel,
        newItem: NoticeModel,
      ): Boolean {
        return oldItem == newItem
      }
    }
  }
}
