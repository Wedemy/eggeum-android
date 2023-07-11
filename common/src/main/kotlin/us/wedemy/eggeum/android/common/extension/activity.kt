/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.extension

import android.app.Activity
import android.content.Intent
import android.os.Build

inline fun <reified T : Activity> Activity.changeActivityWithAnimation(
  intentBuilder: Intent.() -> Intent = { this },
  finishActivity: Boolean = true,
) {
  startActivity(Intent(this, T::class.java).intentBuilder())
  if (Build.VERSION.SDK_INT >= 34) {
    overrideActivityTransition(
      Activity.OVERRIDE_TRANSITION_OPEN,
      android.R.anim.fade_in,
      android.R.anim.fade_out,
    )
  } else {
    @Suppress("DEPRECATION")
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
  }
  if (finishActivity) finish()
}
