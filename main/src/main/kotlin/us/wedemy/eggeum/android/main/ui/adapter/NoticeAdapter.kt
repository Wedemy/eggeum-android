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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.ItemNoticeEmptyBinding
import us.wedemy.eggeum.android.main.databinding.ItemNoticeListBinding
import us.wedemy.eggeum.android.main.databinding.ItemNoticeSearchBinding
import us.wedemy.eggeum.android.main.databinding.ItemNoticeTitleBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NoticeEmptyViewHolder
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NoticeListItemViewHolder
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NoticeSearchViewHolder
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NoticeTitleViewHolder
import us.wedemy.eggeum.android.main.ui.myaccount.NoticeItemClickListener
import us.wedemy.eggeum.android.main.ui.myaccount.NoticeUiModel

class NoticeAdapter(private var clickListener: NoticeItemClickListener) :
  ListAdapter<NoticeUiModel, RecyclerView.ViewHolder>(NoticeItemDiffCallback) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      R.layout.item_notice_empty -> NoticeEmptyViewHolder(
        ItemNoticeEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false),
      )
      R.layout.item_notice_title -> NoticeTitleViewHolder(
        ItemNoticeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
      )
      R.layout.item_notice_search -> NoticeSearchViewHolder(
        ItemNoticeSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
      )
      R.layout.item_notice_list -> NoticeListItemViewHolder(
        ItemNoticeListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
      )
      else -> error("Unknown viewType $viewType")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (val uiModel = getItem(position)) {
      is NoticeUiModel.NoticeEmptyItem -> (holder as NoticeEmptyViewHolder)
      is NoticeUiModel.NoticeTitleItem -> (holder as NoticeTitleViewHolder)
      is NoticeUiModel.NoticeSearchItem -> (holder as NoticeSearchViewHolder).apply {
        binding.root.setOnClickListener {
          clickListener.onSearchTextfieldClick(binding.tietNotice.text.toString())
        }
      }
      is NoticeUiModel.NoticeListItem -> (holder as NoticeListItemViewHolder).apply {
        bind(uiModel.notice)
        with(binding) {
          clNotice.setOnClickListener {
            uiModel.notice.isExpanded = !uiModel.notice.isExpanded
            toggleLayout(uiModel.notice.isExpanded, ivNoticeExpand)
            notifyItemChanged(position)
          }
        }
      }
    }
  }

  override fun getItemViewType(position: Int): Int {
    return when (getItem(position)) {
      is NoticeUiModel.NoticeEmptyItem -> R.layout.item_notice_empty
      is NoticeUiModel.NoticeTitleItem -> R.layout.item_notice_title
      is NoticeUiModel.NoticeSearchItem -> R.layout.item_notice_search
      is NoticeUiModel.NoticeListItem -> R.layout.item_notice_list
    }
  }

  private fun toggleLayout(isExpanded: Boolean, view: View) {
    if (isExpanded) {
      view.animate().setDuration(200).rotation(180f)
    } else {
      view.animate().setDuration(200).rotation(0f)
    }
  }

  private companion object {
    private val NoticeItemDiffCallback = object : DiffUtil.ItemCallback<NoticeUiModel>() {
      override fun areItemsTheSame(oldItem: NoticeUiModel, newItem: NoticeUiModel) = oldItem === newItem
      override fun areContentsTheSame(oldItem: NoticeUiModel, newItem: NoticeUiModel) = oldItem == newItem
    }
  }
}
