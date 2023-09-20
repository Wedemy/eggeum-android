/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.util

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

object ToggleAnimation {
  fun toggleArrow(view: View, isExpanded: Boolean): Boolean {
    return if (isExpanded) {
      view.animate().setDuration(200).rotation(180f)
      true
    } else {
      view.animate().setDuration(200).rotation(0f)
      false
    }
  }

  fun expand(view: View) {
    val animation = expandAction(view)
    view.startAnimation(animation)
  }

  private fun expandAction(view: View): Animation {
    view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val actualHeight = view.measuredHeight

    view.layoutParams.height = 0
    view.visibility = View.VISIBLE

    val animation = object : Animation() {
      override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        view.apply {
          layoutParams.height = if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT
          else (actualHeight * interpolatedTime).toInt()
          requestLayout()
        }
      }
    }
    animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()
    view.startAnimation(animation)

    return animation
  }

  fun collapse(view: View) {
    val actualHeight = view.measuredHeight
    val animation = object : Animation() {
      override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        if (interpolatedTime == 1f) {
          view.visibility = View.GONE
        } else {
          view.apply {
            layoutParams.height = (actualHeight - (actualHeight * interpolatedTime)).toInt()
            requestLayout()
          }
        }
      }
    }
    animation.duration = (actualHeight / view.context.resources.displayMetrics.density).toLong()
    view.startAnimation(animation)
  }
}
