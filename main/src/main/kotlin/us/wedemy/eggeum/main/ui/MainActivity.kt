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

package us.wedemy.eggeum.main.ui

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.common.ui.BaseActivity
import us.wedemy.eggeum.main.R
import us.wedemy.eggeum.main.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity() {
  override val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

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
