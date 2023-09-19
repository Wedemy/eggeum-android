/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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
import us.wedemy.eggeum.android.main.ui.myaccount.NoticeUiModel

class NoticeAdapter(
  private var noticeList: List<NoticeUiModel> = emptyList(),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      R.layout.item_notice_empty -> NoticeEmptyViewHolder(
        ItemNoticeEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
      R.layout.item_notice_title -> NoticeTitleViewHolder(
        ItemNoticeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
      R.layout.item_notice_search -> NoticeSearchViewHolder(
        ItemNoticeSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
      R.layout.item_notice_list -> NoticeListItemViewHolder(
        ItemNoticeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
      else -> throw IllegalStateException("Unknown viewType $viewType")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (val uiModel = noticeList[position]) {
      is NoticeUiModel.NoticeEmptyItem -> (holder as NoticeEmptyViewHolder)
      is NoticeUiModel.NoticeTitleItem -> (holder as NoticeTitleViewHolder)
      is NoticeUiModel.NoticeSearchItem -> (holder as NoticeSearchViewHolder)
      is NoticeUiModel.NoticeListItem -> (holder as NoticeListItemViewHolder).bind(uiModel.notice)
    }
  }

  override fun getItemCount() = noticeList.size

  override fun getItemViewType(position: Int): Int {
    return when (noticeList[position]) {
      is NoticeUiModel.NoticeEmptyItem -> R.layout.item_notice_empty
      is NoticeUiModel.NoticeTitleItem -> R.layout.item_notice_title
      is NoticeUiModel.NoticeSearchItem -> R.layout.item_notice_search
      is NoticeUiModel.NoticeListItem -> R.layout.item_notice_list
    }
  }
}
