/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.initialize

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.crashlytics.FirebaseCrashlytics
import us.wedemy.eggeum.android.BuildConfig

class FirebaseCrashlyticsInitializer : Initializer<Unit> {
  override fun create(context: Context) {
    FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
  }

  override fun dependencies(): List<Class<out Initializer<*>>> {
    return emptyList()
  }
}
