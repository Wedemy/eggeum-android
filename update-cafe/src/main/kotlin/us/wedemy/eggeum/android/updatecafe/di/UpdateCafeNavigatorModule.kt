/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.updatecafe.di

import android.app.Activity
import android.content.Intent
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.common.extension.startActivityWithAnimation
import us.wedemy.eggeum.android.navigator.UpdateCafeNavigator
import us.wedemy.eggeum.android.updatecafe.ui.UpdateCafeActivity

internal class UpdateCafeNavigatorImpl @Inject constructor() : UpdateCafeNavigator {
  override fun navigateFrom(
    activity: Activity,
    intentBuilder: Intent.() -> Intent,
    withFinish: Boolean,
  ) {
    activity.startActivityWithAnimation<UpdateCafeActivity>(
      intentBuilder = intentBuilder,
      withFinish = withFinish,
    )
  }
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UpdateCafeNavigatorModule {
  @Singleton
  @Binds
  abstract fun bindUpdateCafeNavigator(updateCafeNavigatorImpl: UpdateCafeNavigatorImpl): UpdateCafeNavigator
}
