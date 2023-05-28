/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.cake.preview

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Space
import us.wedemy.eggeum.cake.CakeButton
import us.wedemy.eggeum.cake.util.px

class PreviewActivity : Activity() {
  @Suppress("PrivatePropertyName")
  private val SN = this::class.java.simpleName

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    preview {
      LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = LinearLayout.LayoutParams(
          200.px,
          LinearLayout.LayoutParams.WRAP_CONTENT,
        )
        repeat(2) { index ->
          addView(
            CakeButton(this@PreviewActivity)
              .apply {
                text = SN
                enable = index != 0
              }
          )
          if (index == 0) {
            addView(
              Space(this@PreviewActivity)
                .apply {
                  layoutParams = ViewGroup.LayoutParams(100.px, 30.px)
                }
            )
          }
        }
      }
    }
  }

  private fun preview(view: () -> View) {
    val rootView = FrameLayout(this).apply {
      layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT,
      )
    }
    val centerView = FrameLayout(this).apply {
      layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT,
        Gravity.CENTER,
      )
    }
    centerView.addView(view())
    rootView.addView(centerView)
    setContentView(rootView)
  }
}
