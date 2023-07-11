/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.ActivityMainBinding
import us.wedemy.eggeum.common.ui.BaseActivity

// TODO 화면 위아래 하얀 공백 제거
@AndroidEntryPoint
class MainActivity : BaseActivity() {
  override val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

  override var statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
  override var navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)

  private val navController
    get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initNavigation()
  }

  private fun initNavigation() {
    binding.bnvMain.apply {
      navController?.let { setupWithNavController(it) }
      itemIconTintList = null
    }
  }

  override fun onSupportNavigateUp() = navController?.navigateUp() ?: false
}
