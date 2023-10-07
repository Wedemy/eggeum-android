/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.login.di

import android.app.Activity
import android.content.Intent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.common.extension.startActivityWithAnimation
import us.wedemy.eggeum.android.login.LoginActivity
import us.wedemy.eggeum.android.navigator.LoginNavigator

internal class LoginNavigatorImpl @Inject constructor() : LoginNavigator {
  override fun navigateFrom(
    activity: Activity,
    intentBuilder: Intent.() -> Intent,
    withFinish: Boolean,
  ) {
    activity.startActivityWithAnimation<LoginActivity>(
      intentBuilder = intentBuilder,
      withFinish = withFinish,
    )
  }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LoginNavigatorModule {
  @Singleton
  @Binds
  abstract fun bindLoginNavigator(loginNavigatorImpl: LoginNavigatorImpl): LoginNavigator
}
