/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.navigator

import android.app.Activity
import android.content.Intent

interface Navigator {
  fun navigateFrom(
    activity: Activity,
    intentBuilder: Intent.() -> Intent = { this },
    withFinish: Boolean = false,
  )
}
