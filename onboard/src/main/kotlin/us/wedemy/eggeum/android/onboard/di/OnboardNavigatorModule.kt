/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.onboard.di

import android.app.Activity
import android.content.Intent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.common.extension.startActivityWithAnimation
import us.wedemy.eggeum.android.navigator.OnboardNavigator
import us.wedemy.eggeum.android.onboard.ui.OnboardActivity

internal class OnboardNavigatorImpl @Inject constructor() : OnboardNavigator {
  override fun navigateFrom(
    activity: Activity,
    intentBuilder: Intent.() -> Intent,
    withFinish: Boolean,
  ) {
    activity.startActivityWithAnimation<OnboardActivity>(
      intentBuilder = intentBuilder,
      withFinish = withFinish,
    )
  }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class OnboardNavigatorModule {
  @Singleton
  @Binds
  abstract fun bindOnboardNavigator(onboardNavigatorImpl: OnboardNavigatorImpl): OnboardNavigator
}
