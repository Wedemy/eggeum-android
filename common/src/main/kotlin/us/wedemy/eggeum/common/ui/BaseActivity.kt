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

abstract class BaseActivity : AppCompatActivity {
  private var statusBarStyle: SystemBarStyle
  private var navigationBarStyle: SystemBarStyle

  constructor(
    statusBarStyle: SystemBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
    navigationBarStyle: SystemBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
  ) {
    this.statusBarStyle = statusBarStyle
    this.navigationBarStyle = navigationBarStyle
  }

  abstract val binding: ViewBinding

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
