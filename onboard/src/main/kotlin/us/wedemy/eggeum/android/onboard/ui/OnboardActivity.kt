/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.onboard.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.InsetterApplyTypeDsl
import dev.chrisbanes.insetter.applyInsetter
import javax.inject.Inject
import us.wedemy.eggeum.android.common.base.BaseActivity
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.root.applyInsetter {
      type(
        ime = false,
        statusBars = true,
        navigationBars = true,
        f = InsetterApplyTypeDsl::padding,
      )
    }
  }

  fun navigateToMain() {
    mainNavigator.navigateFrom(
      activity = this,
      withFinish = true,
    )
  }
}
