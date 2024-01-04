/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

fun fadeInView(view: View) {
  view.apply {
    alpha = 0f
    visibility = View.VISIBLE
    animate()
      .alpha(1f)
      .setDuration(200)
      .setListener(null)
  }
}

fun fadeOutView(view: View) {
  view.animate()
    .alpha(0f)
    .setDuration(200)
    .setListener(object : AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator) {
        view.visibility = View.INVISIBLE
      }
    })
}