/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.updatecafe.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.InsetterApplyTypeDsl
import dev.chrisbanes.insetter.applyInsetter
import javax.inject.Inject
import us.wedemy.eggeum.android.common.base.BaseActivity
import us.wedemy.eggeum.android.navigator.MainNavigator
import us.wedemy.eggeum.android.updatecafe.R
import us.wedemy.eggeum.android.updatecafe.databinding.ActivityUpdateCafeBinding
import us.wedemy.eggeum.android.updatecafe.viewmodel.ProposeCafeInfoViewModel

@AndroidEntryPoint
class UpdateCafeActivity : BaseActivity() {
  override val binding by lazy { ActivityUpdateCafeBinding.inflate(layoutInflater) }

  val viewModel by viewModels<ProposeCafeInfoViewModel>()

  private val navController
    get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

  @Inject
  lateinit var mainNavigator: MainNavigator

  override fun onSupportNavigateUp() = navController?.navigateUp() ?: false

  fun navigateToMain() {
    mainNavigator.navigateFrom(
      activity = this,
      withFinish = true,
    )
  }

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
