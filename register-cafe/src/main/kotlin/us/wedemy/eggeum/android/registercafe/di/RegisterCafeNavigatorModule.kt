/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.registercafe.di

import android.app.Activity
import android.content.Intent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.common.extension.startActivityWithAnimation
import us.wedemy.eggeum.android.navigator.RegisterCafeNavigator
import us.wedemy.eggeum.android.registercafe.ui.RegisterCafeActivity

internal class RegisterCafeNavigatorImpl @Inject constructor() : RegisterCafeNavigator {
  override fun navigateFrom(
    activity: Activity,
    intentBuilder: Intent.() -> Intent,
    withFinish: Boolean,
  ) {
    activity.startActivityWithAnimation<RegisterCafeActivity>(
      intentBuilder = intentBuilder,
      withFinish = withFinish,
    )
  }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RegisterCafeNavigatorModule {
  @Singleton
  @Binds
  abstract fun bindRegisterCafeNavigator(registerCafeNavigatorImpl: RegisterCafeNavigatorImpl): RegisterCafeNavigator
}
