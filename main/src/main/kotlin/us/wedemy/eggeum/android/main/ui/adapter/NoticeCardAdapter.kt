/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import us.wedemy.eggeum.android.common.extension.layoutInflater
import us.wedemy.eggeum.android.main.databinding.ItemNoticeCardBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NoticeCardViewHolder
import us.wedemy.eggeum.android.main.ui.item.NoticeCardItem

class NoticeCardAdapter(
  private var noticeList: List<NoticeCardItem> = emptyList(),
  private val clickListener: (Int) -> Unit,
) : RecyclerView.Adapter<NoticeCardViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    NoticeCardViewHolder(ItemNoticeCardBinding.inflate(parent.context.layoutInflater, parent, false))

  override fun onBindViewHolder(holder: NoticeCardViewHolder, position: Int) {
    val notice = noticeList[position]
    with(holder) {
      bind(notice)
      binding.root.setOnClickListener {
        clickListener(bindingAdapterPosition)
      }
    }
  }

  override fun getItemCount() = noticeList.size
}
