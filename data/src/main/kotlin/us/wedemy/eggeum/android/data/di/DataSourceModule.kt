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
import us.wedemy.eggeum.android.data.datasource.file.FileDataSource
import us.wedemy.eggeum.android.data.datasource.file.FileDataSourceImpl
import us.wedemy.eggeum.android.data.datasource.login.LoginLocalDataSource
import us.wedemy.eggeum.android.data.datasource.login.LoginLocalDataSourceImpl
import us.wedemy.eggeum.android.data.datasource.login.LoginRemoteDataSource
import us.wedemy.eggeum.android.data.datasource.login.LoginRemoteDataSourceImpl
import us.wedemy.eggeum.android.data.datasource.notice.NoticeDataSource
import us.wedemy.eggeum.android.data.datasource.notice.NoticeDataSourceImpl
import us.wedemy.eggeum.android.data.datasource.place.PlaceDataSource
import us.wedemy.eggeum.android.data.datasource.place.PlaceDataSourceImpl
import us.wedemy.eggeum.android.data.datasource.report.ReportDataSource
import us.wedemy.eggeum.android.data.datasource.report.ReportDataSourceImpl
import us.wedemy.eggeum.android.data.datasource.user.UserDataSource
import us.wedemy.eggeum.android.data.datasource.user.UserDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
  @Binds
  @Singleton
  abstract fun bindLoginLocalDataSource(loginLocalDataSourceImpl: LoginLocalDataSourceImpl): LoginLocalDataSource

  @Binds
  @Singleton
  abstract fun bindLoginRemoteDataSource(loginRemoteDataSourceImpl: LoginRemoteDataSourceImpl): LoginRemoteDataSource

  @Binds
  @Singleton
  abstract fun bindNoticeDataSource(noticeDataSourceImpl: NoticeDataSourceImpl): NoticeDataSource

  @Binds
  @Singleton
  abstract fun bindPlaceDataSource(placeDataSourceImpl: PlaceDataSourceImpl): PlaceDataSource

  @Binds
  @Singleton
  abstract fun bindReportDataSource(reportDataSourceImpl: ReportDataSourceImpl): ReportDataSource

  @Binds
  @Singleton
  abstract fun bindUserDataSource(userDataSourceImpl: UserDataSourceImpl): UserDataSource

  @Binds
  @Singleton
  abstract fun bindFileDataSource(fileDataSourceImpl: FileDataSourceImpl): FileDataSource
}
