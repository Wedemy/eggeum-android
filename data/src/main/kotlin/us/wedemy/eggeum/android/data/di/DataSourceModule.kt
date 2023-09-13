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
import us.wedemy.eggeum.android.data.datasource.LoginLocalDataSource
import us.wedemy.eggeum.android.data.datasource.LoginLocalDataSourceProvider
import us.wedemy.eggeum.android.data.datasource.LoginRemoteDataSource
import us.wedemy.eggeum.android.data.datasource.LoginRemoteDataSourceProvider

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
  @Binds
  @Singleton
  abstract fun provideLoginLocalDataSource(loginLocalDataSourceProvider: LoginLocalDataSourceProvider): LoginLocalDataSource

  @Binds
  @Singleton
  abstract fun provideLoginRemoteDataSource(loginRemoteDataSourceProvider: LoginRemoteDataSourceProvider): LoginRemoteDataSource
}
