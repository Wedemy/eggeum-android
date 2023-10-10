/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.util

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

abstract class OnMenuItemSingleClickListener(private val toolbar: Toolbar, private val minClickInterval: Long = 500) :
  Toolbar.OnMenuItemClickListener {

  private var enabled = true
  private val enableAgain = Runnable { enabled = true }

  override fun onMenuItemClick(item: MenuItem): Boolean {
    return if (enabled) {
      enabled = false
      toolbar.postDelayed(enableAgain, minClickInterval)
      onMenuSingleClick(item)
    } else {
      false
    }
  }

  abstract fun onMenuSingleClick(item: MenuItem): Boolean
}

fun Toolbar.setOnMenuItemSingleClickListener(minClickInterval: Long = 500, onMenuClick: (item: MenuItem) -> Boolean) {
  this.setOnMenuItemClickListener(object :
    OnMenuItemSingleClickListener(this@setOnMenuItemSingleClickListener, minClickInterval) {
    override fun onMenuSingleClick(item: MenuItem): Boolean {
      return onMenuClick(item)
    }
  })
}
