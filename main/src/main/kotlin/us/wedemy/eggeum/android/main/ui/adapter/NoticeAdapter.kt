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
import us.wedemy.eggeum.android.main.databinding.ItemNoticeBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NoticeViewHolder
import us.wedemy.eggeum.android.main.ui.item.NoticeItem

class NoticeAdapter(
  private var noticeList: List<NoticeItem> = emptyList(),
  private val clickListener: (Int) -> Unit,
) : RecyclerView.Adapter<NoticeViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    NoticeViewHolder(ItemNoticeBinding.inflate(parent.context.layoutInflater, parent, false))

  override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
    val notice = noticeList[position]
    with(holder) {
      bind(notice)
      binding.root.setOnClickListener {
        clickListener(adapterPosition)
      }
    }
  }

  override fun getItemCount() = noticeList.size

  fun submitList(list: List<NoticeItem>) {
    noticeList = list
  }
}
