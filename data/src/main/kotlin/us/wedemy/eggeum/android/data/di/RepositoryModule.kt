/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import us.wedemy.eggeum.android.data.repository.EnumRepositoryProvider
import us.wedemy.eggeum.android.data.repository.LoginRepositoryProvider
import us.wedemy.eggeum.android.domain.repository.EnumRepository
import us.wedemy.eggeum.android.domain.repository.LoginRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
  @Binds
  @Singleton
  abstract fun provideLoginRepository(loginRepositoryProvider: LoginRepositoryProvider): LoginRepository

  @Binds
  @Singleton
  abstract fun provideEnumRepository(enumRepositoryProvider: EnumRepositoryProvider): EnumRepository
}
