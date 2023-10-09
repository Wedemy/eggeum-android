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
import us.wedemy.eggeum.android.main.ui.item.NoticeItem

class NoticeListItemViewHolder(val binding: ItemNoticeListBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(notice: NoticeItem) {
    binding.apply {
      tvNoticeTitle.text = notice.title
      tvNoticeDate.text = notice.date
      tvNoticeDescription.text = notice.description
//      clNotice.setOnClickListener {
//        val show = toggleLayout(!notice.isExpanded, ivNoticeExpand, llLayoutExpand)
//        notice.isExpanded = show
//      }
      clNotice.setOnClickListener {
        toggleContent(binding.llLayoutExpand)
      }
    }
  }

//  private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
//    ToggleAnimation.toggleArrow(view, isExpanded)
//    if (isExpanded) {
//      ToggleAnimation.expand(layoutExpand)
//    } else {
//      ToggleAnimation.collapse(layoutExpand)
//    }
//    return isExpanded
//  }

  private fun toggleContent(hiddenView: View) {
    if (hiddenView.visibility == View.VISIBLE) {
      // collapseView(hiddenView)
      with(binding) {
        llLayoutExpand.visibility = View.GONE
        ivNoticeExpand.animate().setDuration(200).rotation(180f)
      }
    } else {
      // expandView(hiddenView)
      with(binding) {
        llLayoutExpand.visibility = View.VISIBLE
        ivNoticeExpand.animate().setDuration(200).rotation(0f)
      }
    }
  }

//  private fun expandView(view: View) {
//    view.measure(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
//    val targetHeight = view.measuredHeight
//
//    view.layoutParams.height = 0
//    view.visibility = View.VISIBLE
//    val animator = ValueAnimator.ofInt(0, targetHeight)
//    animator.addUpdateListener { animation ->
//      view.layoutParams.height = (animation.animatedValue as Int)
//      view.requestLayout()
//    }
//    animator.duration = 300
//    animator.start()
//  }
//
//  private fun collapseView(view: View) {
//    val initialHeight = view.measuredHeight
//    val animator = ValueAnimator.ofInt(initialHeight, 0)
//    animator.addUpdateListener { animation ->
//      view.layoutParams.height = (animation.animatedValue as Int)
//      view.requestLayout()
//    }
//    animator.addListener(object : AnimatorListenerAdapter() {
//      override fun onAnimationEnd(animation: Animator) {
//        view.visibility = View.GONE
//      }
//    })
//    animator.duration = 300
//    animator.start()
//  }
}
