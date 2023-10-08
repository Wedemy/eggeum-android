/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.onboard.ui

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import us.wedemy.eggeum.android.common.ui.BaseActivity
import us.wedemy.eggeum.android.navigator.MainNavigator
import us.wedemy.eggeum.android.onboard.R
import us.wedemy.eggeum.android.onboard.databinding.ActivityOnboardBinding

@AndroidEntryPoint
class OnboardActivity : BaseActivity() {
  override val binding by lazy { ActivityOnboardBinding.inflate(layoutInflater) }
  private val navController: NavController?
    get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

  @Inject
  lateinit var mainNavigator: MainNavigator

  override fun onSupportNavigateUp(): Boolean = navController?.navigateUp() ?: false

  fun navigateToMain() {
    mainNavigator.navigateFrom(
      activity = this,
      withFinish = true,
    )
  }
}
