/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.updatecafe.ui

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseActivity
import us.wedemy.eggeum.android.updatecafe.R
import us.wedemy.eggeum.android.updatecafe.databinding.ActivityUpdateCafeBinding

@AndroidEntryPoint
class UpdateCafeActivity : BaseActivity() {
  override val binding by lazy { ActivityUpdateCafeBinding.inflate(layoutInflater) }
  private val navController
    get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

  override fun onSupportNavigateUp() = navController?.navigateUp() ?: false
}