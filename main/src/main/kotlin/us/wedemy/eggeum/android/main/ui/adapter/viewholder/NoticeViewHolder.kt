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
import us.wedemy.eggeum.android.main.databinding.ItemNoticeBinding
import us.wedemy.eggeum.android.main.ui.item.NoticeItem

class NoticeViewHolder(val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(notice: NoticeItem) {
    with(binding) {
      tvNoticeTitle.text = notice.title
      tvNoticeDate.text = notice.date
      tvNoticeDescription.text = notice.description

      root.setOnClickListener {
        val show = toggleLayout(!notice.isExpanded, ivNotice, llLayoutExpand)
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
