/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.onboard.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import us.wedemy.eggeum.onboard.R
import us.wedemy.eggeum.onboard.databinding.ActivityOnboardBinding
import us.wedemy.eggeum.onboard.ui.base.BaseActivity

class OnboardActivity : BaseActivity<ActivityOnboardBinding>(R.layout.activity_onboard) {

  override fun getViewBinding() = ActivityOnboardBinding.inflate(layoutInflater)

  private val navController: NavController?
    get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    navController?.setGraph(R.navigation.nav_onboard)
  }

  override fun onSupportNavigateUp(): Boolean = navController?.navigateUp() ?: false
}