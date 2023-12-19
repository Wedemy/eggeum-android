/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.SystemBarStyle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import us.wedemy.eggeum.android.common.ui.BaseActivity
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.ActivityMainBinding
import us.wedemy.eggeum.android.navigator.LoginNavigator
import us.wedemy.eggeum.android.navigator.UpdateCafeNavigator

@AndroidEntryPoint
class MainActivity : BaseActivity() {
  override val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

  override var statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
  override var navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)

  @Inject
  lateinit var loginNavigator: LoginNavigator

  @Inject
  lateinit var updateCafeNavigator: UpdateCafeNavigator

  private val navController
    get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initNavigation()
  }

  private fun initNavigation() {
    binding.bnvMain.apply {
      navController?.let(::setupWithNavController)
      itemIconTintList = null
      navController?.addOnDestinationChangedListener { _, destination, _ ->
        visibility = when (destination.id) {
          R.id.fragment_home, R.id.fragment_search, R.id.fragment_my_account -> View.VISIBLE
          else -> View.GONE
        }
      }
    }
  }

  override fun onSupportNavigateUp() = navController?.navigateUp() ?: false

  fun navigateToLogin() {
    loginNavigator.navigateFrom(
      activity = this,
      withFinish = true,
    )
  }

  fun navigateToUpdateCafe() {
    updateCafeNavigator.navigateFrom(
      activity = this,
      withFinish = false,
    )
  }
}
