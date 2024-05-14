/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.di

import android.app.Activity
import android.content.Intent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.common.extension.startActivityWithAnimation
import us.wedemy.eggeum.android.main.ui.MainActivity
import us.wedemy.eggeum.android.navigator.MainNavigator

internal class MainNavigatorImpl @Inject constructor() : MainNavigator {
  override fun navigateFrom(
    activity: Activity,
    intentBuilder: Intent.() -> Intent,
    withFinish: Boolean,
  ) {
    activity.startActivityWithAnimation<MainActivity>(
      intentBuilder = intentBuilder,
      withFinish = withFinish,
    )
  }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MainNavigatorModule {
  @Singleton
  @Binds
  abstract fun bindMainNavigator(mainNavigatorImpl: MainNavigatorImpl): MainNavigator
}
