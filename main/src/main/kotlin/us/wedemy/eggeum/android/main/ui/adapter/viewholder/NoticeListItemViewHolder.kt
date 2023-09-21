/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import us.wedemy.eggeum.android.common.util.ToggleAnimation
import us.wedemy.eggeum.android.main.databinding.ItemNoticeListBinding
import us.wedemy.eggeum.android.main.ui.item.NoticeItem

class NoticeListItemViewHolder(val binding: ItemNoticeListBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(notice: NoticeItem) {
    binding.apply {
      tvNoticeTitle.text = notice.title
      tvNoticeDate.text = notice.date
      tvNoticeDescription.text = notice.description
      clNotice.setOnClickListener {
        val show = toggleLayout(!notice.isExpanded, ivNoticeExpand, llLayoutExpand)
        notice.isExpanded = show
      }
    }
  }

  private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
    ToggleAnimation.toggleArrow(view, isExpanded)
    if (isExpanded) {
      ToggleAnimation.expand(layoutExpand)
    } else {
      ToggleAnimation.collapse(layoutExpand)
    }
    return isExpanded
  }
}
