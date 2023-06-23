/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    @Suppress("SetTextI18n")
    setContentView(TextView(this).apply { text = "Hello, World!" })

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      splashScreen.setOnExitAnimationListener { splashScreenView ->
        ObjectAnimator.ofFloat(splashScreenView, View.ALPHA, 1f, 0f).run {
          interpolator = AnticipateInterpolator()
          duration = 200L
          doOnEnd { splashScreenView.remove() }
          start()
        }
      }
    }

    /*binding.root.viewTreeObserver.addOnPreDrawListener(
      object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
          return if (isReady) {
            binding.root.viewTreeObserver.removeOnPreDrawListener(this)
            true
          } else {
            false
          }
        }
      }
    )*/
  }
}
