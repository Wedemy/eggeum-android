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

package us.wedemy.eggeum.android

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.SystemBarStyle
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.startActivityWithAnimation
import us.wedemy.eggeum.android.common.ui.BaseActivity
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

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      splashScreen.setOnExitAnimationListener { splashScreenView ->
        ObjectAnimator.ofFloat(splashScreenView, View.ALPHA, 1f, 0f).run {
          interpolator = LinearInterpolator()
          duration = 500L
          doOnEnd { splashScreenView.remove() }
          start()
        }
      }
    }

//    binding.lavLogo.addAnimatorListener(
//      object : Animator.AnimatorListener {
//        override fun onAnimationRepeat(animation: Animator) = Unit
//        override fun onAnimationStart(animation: Animator) = Unit
//        override fun onAnimationCancel(animation: Animator) = Unit
//        override fun onAnimationEnd(animation: Animator) {
//          lifecycleScope.launch {
//            delay(50)
//            changeActivityWithAnimation<LoginActivity>()
//          }
//        }
//      },
//    )
    initObserver()
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.navigateToLoginEvent.collect {
          delay(500L)
          startActivityWithAnimation<LoginActivity>()
        }
      }
      launch {
        viewModel.navigateToMainEvent.collect {
          delay(500L)
          startActivityWithAnimation<MainActivity>()
        }
      }
    }
  }
}
