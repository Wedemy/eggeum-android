/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.common.util

import android.app.Activity
import android.content.Intent

inline fun <reified T : Activity> Activity.changeActivityWithAnimation(
  intentBuilder: Intent.() -> Intent = { this },
  finishActivity: Boolean = true,
) {
  startActivity(Intent(this, T::class.java).intentBuilder())
  overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
  if (finishActivity) finish()
}
