/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.common.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import dev.chrisbanes.insetter.InsetterApplyTypeDsl
import dev.chrisbanes.insetter.applyInsetter

abstract class BaseActivity() : AppCompatActivity() {
  abstract val binding: ViewBinding

  private val statusBarStyle: SystemBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
  private val navigationBarStyle: SystemBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)

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
