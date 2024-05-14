/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package us.wedemy.eggeum.android.common.base

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import dev.chrisbanes.insetter.InsetterApplyTypeDsl
import dev.chrisbanes.insetter.applyInsetter

abstract class BaseActivity : AppCompatActivity() {
  protected abstract val binding: ViewBinding

  protected open var statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
  protected open var navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)

  override fun onCreate(savedInstanceState: Bundle?) {
    enableEdgeToEdge(statusBarStyle = statusBarStyle, navigationBarStyle = navigationBarStyle)
    super.onCreate(savedInstanceState)
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//      window.insetsController?.setSystemBarsAppearance(
//        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
//        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
//      )
//    } else {
//      @Suppress("DEPRECATION")
//      window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//    }
    binding.root.applyInsetter {
      type(
        ime = false,
        statusBars = true,
        navigationBars = false,
        f = InsetterApplyTypeDsl::padding,
      )
    }
    setContentView(binding.root)
  }
}
