/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.registercafe.ui

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.common.ui.BaseActivity
import us.wedemy.eggeum.registercafe.R
import us.wedemy.eggeum.registercafe.databinding.ActivityRegisterCafeBinding

@AndroidEntryPoint
class RegisterCafeActivity : BaseActivity() {
  override val binding by lazy { ActivityRegisterCafeBinding.inflate(layoutInflater) }

  private val navController
    get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    navController?.setGraph(R.navigation.nav_register_cafe)
  }

  override fun onSupportNavigateUp() = navController?.navigateUp() ?: false
}
