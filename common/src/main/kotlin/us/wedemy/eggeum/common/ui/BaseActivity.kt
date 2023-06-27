/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("MemberVisibilityCanBePrivate")

package us.wedemy.eggeum.common.ui

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
    setContentView(binding.root)
    binding.root.applyInsetter {
      type(statusBars = true, f = InsetterApplyTypeDsl::padding)
      type(navigationBars = true, f = InsetterApplyTypeDsl::padding)
    }
  }
}
