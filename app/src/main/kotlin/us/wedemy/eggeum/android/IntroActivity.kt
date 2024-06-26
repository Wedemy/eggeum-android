/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.base.BaseActivity
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.startActivityWithAnimation
import us.wedemy.eggeum.android.databinding.ActivityIntroBinding
import us.wedemy.eggeum.android.login.LoginActivity
import us.wedemy.eggeum.android.main.ui.MainActivity
import us.wedemy.eggeum.android.navigator.LoginNavigator
import us.wedemy.eggeum.android.navigator.MainNavigator

@AndroidEntryPoint
class IntroActivity : BaseActivity() {
  override val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }

  override var statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
  override var navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)

  private val viewModel by viewModels<IntroViewModel>()

  @Inject
  lateinit var loginNavigator: LoginNavigator

  @Inject
  lateinit var mainNavigator: MainNavigator

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    initObserver()
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.navigateToLoginEvent.collect {
          delay(1500L)
          startActivityWithAnimation<LoginActivity>()
        }
      }
      launch {
        viewModel.navigateToMainEvent.collect {
          delay(1500L)
          startActivityWithAnimation<MainActivity>()
        }
      }
    }
  }
}
