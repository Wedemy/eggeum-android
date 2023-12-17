/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import us.wedemy.eggeum.android.main.databinding.ItemNoticeListBinding
import us.wedemy.eggeum.android.main.model.NoticeModel

class NoticeListItemViewHolder(val binding: ItemNoticeListBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(notice: NoticeModel) {
    binding.apply {
      tvNoticeTitle.text = notice.title
      tvNoticeDate.text = notice.date
      tvNoticeDescription.text = notice.description
      ivNoticeExpand.rotation = if (notice.isExpanded) 180f else 0f
      llLayoutExpand.visibility = if (notice.isExpanded) View.VISIBLE else View.GONE
    }
  }
}
