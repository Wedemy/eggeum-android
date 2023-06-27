/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.SystemBarStyle
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.databinding.ActivityIntroBinding
import us.wedemy.eggeum.common.extension.changeActivityWithAnimation
import us.wedemy.eggeum.common.ui.BaseActivity

class IntroActivity : BaseActivity() {
  override val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }

  override var statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
  override var navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      splashScreen.setOnExitAnimationListener { splashScreenView ->
        ObjectAnimator.ofFloat(splashScreenView, View.ALPHA, 1f, 0f).run {
          interpolator = LinearInterpolator()
          duration = 200L
          doOnEnd { splashScreenView.remove() }
          start()
        }
      }
    }

    binding.lavLogo.addAnimatorListener(
      object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator) = Unit
        override fun onAnimationStart(animation: Animator) = Unit
        override fun onAnimationCancel(animation: Animator) = Unit
        override fun onAnimationEnd(animation: Animator) {
          lifecycleScope.launch {
            delay(50)
            changeActivityWithAnimation<LoginActivity>()
          }
        }
      },
    )
  }
}
