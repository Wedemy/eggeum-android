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
import us.wedemy.eggeum.android.data.repository.EnumRepositoryImpl
import us.wedemy.eggeum.android.data.repository.FileRepositoryImpl
import us.wedemy.eggeum.android.data.repository.LoginRepositoryImpl
import us.wedemy.eggeum.android.data.repository.NoticeRepositoryImpl
import us.wedemy.eggeum.android.data.repository.PlaceRepositoryImpl
import us.wedemy.eggeum.android.data.repository.ReportRepositoryImpl
import us.wedemy.eggeum.android.data.repository.UserRepositoryImpl
import us.wedemy.eggeum.android.domain.repository.EnumRepository
import us.wedemy.eggeum.android.domain.repository.FileRepository
import us.wedemy.eggeum.android.domain.repository.LoginRepository
import us.wedemy.eggeum.android.domain.repository.NoticeRepository
import us.wedemy.eggeum.android.domain.repository.PlaceRepository
import us.wedemy.eggeum.android.domain.repository.ReportRepository
import us.wedemy.eggeum.android.domain.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
  @Binds
  @Singleton
  abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

  @Binds
  @Singleton
  abstract fun bindEnumRepository(enumRepositoryImpl: EnumRepositoryImpl): EnumRepository

  @Binds
  @Singleton
  abstract fun bindNoticeRepository(noticeRepositoryImpl: NoticeRepositoryImpl): NoticeRepository

  @Binds
  @Singleton
  abstract fun bindPlaceRepository(placeRepositoryImpl: PlaceRepositoryImpl): PlaceRepository

  @Binds
  @Singleton
  abstract fun bindReportRepository(reportRepositoryImpl: ReportRepositoryImpl): ReportRepository

  @Binds
  @Singleton
  abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

  @Binds
  @Singleton
  abstract fun bindFileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository
}
