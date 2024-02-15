/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.registercafe.ui

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.InsetterApplyTypeDsl
import dev.chrisbanes.insetter.applyInsetter
import us.wedemy.eggeum.android.registercafe.R
import us.wedemy.eggeum.android.registercafe.databinding.ActivityRegisterCafeBinding
import us.wedemy.eggeum.android.common.base.BaseActivity

@AndroidEntryPoint
class RegisterCafeActivity : BaseActivity() {
  override val binding by lazy { ActivityRegisterCafeBinding.inflate(layoutInflater) }
  private val navController
    get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

  override fun onSupportNavigateUp() = navController?.navigateUp() ?: false

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
}
