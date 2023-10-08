package us.wedemy.eggeum.android.initialize

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber
import us.wedemy.eggeum.android.BuildConfig

class TimberInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

  override fun dependencies(): List<Class<out Initializer<*>>> {
    return emptyList()
  }
}
