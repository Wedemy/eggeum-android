/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.SystemBarStyle
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.InsetterApplyTypeDsl
import dev.chrisbanes.insetter.applyInsetter
import javax.inject.Inject
import us.wedemy.eggeum.android.common.base.BaseActivity
import us.wedemy.eggeum.android.common.model.CafeDetailModel
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
    setupDestinationChangeListener()
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

  private fun setupDestinationChangeListener() {
    navController?.addOnDestinationChangedListener { _, destination, _ ->
      when (destination.id) {
        R.id.fragment_home, R.id.fragment_my_account -> {
          updateSystemBars(Color.TRANSPARENT)
          binding.root.applyInsetter {
            type(
              ime = true,
              statusBars = true,
              navigationBars = false,
              f = InsetterApplyTypeDsl::padding,
            )
          }
        }

        R.id.fragment_cafe_image_detail -> {
          updateSystemBars(ContextCompat.getColor(this, us.wedemy.eggeum.android.design.R.color.muted_900))
          binding.root.applyInsetter {
            type(
              ime = true,
              statusBars = true,
              navigationBars = false,
              f = InsetterApplyTypeDsl::padding,
            )
          }
        }

        R.id.fragment_search, R.id.fragment_map -> {
          updateSystemBars(Color.TRANSPARENT)
          binding.root.applyInsetter {
            type(
              ime = true,
              statusBars = false,
              navigationBars = false,
              f = InsetterApplyTypeDsl::padding,
            )
          }
        }

        else -> {
          updateSystemBars(Color.TRANSPARENT)
          binding.root.applyInsetter {
            type(
              ime = true,
              statusBars = true,
              navigationBars = true,
              f = InsetterApplyTypeDsl::padding,
            )
          }
        }
      }
    }
  }

  private fun updateSystemBars(color: Int) {
    window.statusBarColor = color
    window.navigationBarColor = color

    val isDarkColor =
      color == ContextCompat.getColor(this, us.wedemy.eggeum.android.design.R.color.muted_900)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      WindowCompat.setDecorFitsSystemWindows(window, false)
      val controller = window.insetsController
      if (isDarkColor) {
        controller?.setSystemBarsAppearance(0, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
      } else {
        controller?.setSystemBarsAppearance(
          WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
          WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
        )
      }
    } else {
      @Suppress("DEPRECATION")
      if (isDarkColor) {
        window.decorView.systemUiVisibility = 0
      } else {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
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

  fun navigateToUpdateCafe(cafeDetailInfo: CafeDetailModel) {
    updateCafeNavigator.navigateFrom(
      activity = this,
      intentBuilder = {
        putExtra(KEY_CAFE_DETAIL_INFO, cafeDetailInfo)
      },
      withFinish = false,
    )
  }

  private companion object {
    private const val KEY_CAFE_DETAIL_INFO = "cafe_detail_info"
  }
}
