/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.util

import android.view.View

abstract class OnSingleClickListener(private val minClickInterval: Long = 500) : View.OnClickListener {

  private var enabled = true
  private val enableAgain = Runnable { enabled = true }

  override fun onClick(v: View) {
    if (enabled) {
      enabled = false
      v.postDelayed(enableAgain, minClickInterval)
      onSingleClick(v)
    }
  }

  abstract fun onSingleClick(v: View)
}

fun View.setOnSingleClickListener(minClickInterval: Long = 500, onClick: () -> Unit) {
  this.setOnClickListener(object : OnSingleClickListener(minClickInterval) {
    override fun onSingleClick(v: View) {
      onClick()
    }
  })
}
