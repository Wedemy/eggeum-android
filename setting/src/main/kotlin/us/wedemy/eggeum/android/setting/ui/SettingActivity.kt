/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.setting.ui

import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.setting.R
import us.wedemy.eggeum.android.setting.databinding.ActivitySettingBinding
import us.wedemy.eggeum.android.common.ui.BaseActivity

@AndroidEntryPoint
class SettingActivity : BaseActivity() {
  override val binding by lazy { ActivitySettingBinding.inflate(layoutInflater) }
  private val navController
    get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

  override fun onSupportNavigateUp() = navController?.navigateUp() ?: false
}
