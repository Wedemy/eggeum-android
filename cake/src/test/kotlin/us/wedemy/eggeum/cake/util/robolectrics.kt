/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.cake.util

import android.app.Activity
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

val getActivityViaRobolectric: Activity
  inline get() {
    val activityController = Robolectric.buildActivity(Activity::class.java)
      .also(ActivityController<Activity>::setup)
    return activityController.get()
  }
