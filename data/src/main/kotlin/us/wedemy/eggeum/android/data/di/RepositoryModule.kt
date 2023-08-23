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
import us.wedemy.eggeum.android.data.repository.NoticeRepositoryProvider
import us.wedemy.eggeum.android.data.repository.PlaceRepositoryProvider
import us.wedemy.eggeum.android.data.repository.ReportRepositoryProvider
import us.wedemy.eggeum.android.data.repository.UserRepositoryProvider
import us.wedemy.eggeum.android.domain.repository.EnumRepository
import us.wedemy.eggeum.android.domain.repository.LoginRepository
import us.wedemy.eggeum.android.domain.repository.NoticeRepository
import us.wedemy.eggeum.android.domain.repository.PlaceRepository
import us.wedemy.eggeum.android.domain.repository.ReportRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
  @Binds
  @Singleton
  abstract fun provideLoginRepository(loginRepositoryProvider: LoginRepositoryProvider): LoginRepository

  @Binds
  @Singleton
  abstract fun provideEnumRepository(enumRepositoryProvider: EnumRepositoryProvider): EnumRepository

  @Binds
  @Singleton
  abstract fun provideNoticeRepository(noticeRepositoryProvider: NoticeRepositoryProvider): NoticeRepository

  @Binds
  @Singleton
  abstract fun providePlaceRepository(placeRepositoryProvider: PlaceRepositoryProvider): PlaceRepository

  @Binds
  @Singleton
  abstract fun provideReportRepository(reportRepositoryProvider: ReportRepositoryProvider): ReportRepository

  @Binds
  @Singleton
  abstract fun provideUserRepository(userRepositoryProvider: UserRepositoryProvider): UserRepositoryProvider
}
