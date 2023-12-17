/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import retrofit2.Retrofit
import us.wedemy.eggeum.android.data.service.FileService
import us.wedemy.eggeum.android.data.service.LoginService
import us.wedemy.eggeum.android.data.service.NoticeService
import us.wedemy.eggeum.android.data.service.PlaceService
import us.wedemy.eggeum.android.data.service.ReportService
import us.wedemy.eggeum.android.data.service.UserService

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

  @Singleton
  @Provides
  internal fun provideLoginService(
    @Named("RetrofitAuthHttpClient")
    retrofit: Retrofit,
  ): LoginService {
    return retrofit.create(LoginService::class.java)
  }

  @Singleton
  @Provides
  internal fun provideNoticeService(
    @Named("RetrofitHttpClient")
    retrofit: Retrofit,
  ): NoticeService {
    return retrofit.create(NoticeService::class.java)
  }

  @Singleton
  @Provides
  internal fun providePlaceService(
    @Named("RetrofitHttpClient")
    retrofit: Retrofit,
  ): PlaceService {
    return retrofit.create(PlaceService::class.java)
  }

  @Singleton
  @Provides
  internal fun provideReportService(
    @Named("RetrofitHttpClient")
    retrofit: Retrofit,
  ): ReportService {
    return retrofit.create(ReportService::class.java)
  }

  @Singleton
  @Provides
  internal fun provideUserService(
    @Named("RetrofitHttpClient")
    retrofit: Retrofit,
  ): UserService {
    return retrofit.create(UserService::class.java)
  }

  @Singleton
  @Provides
  internal fun provideFileService(
    @Named("RetrofitFileHttpClient")
    retrofit: Retrofit,
  ): FileService {
    return retrofit.create(FileService::class.java)
  }
}
