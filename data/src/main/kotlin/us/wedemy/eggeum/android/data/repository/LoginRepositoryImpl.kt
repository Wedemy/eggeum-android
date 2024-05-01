/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.data.datasource.login.LoginLocalDataSource
import us.wedemy.eggeum.android.data.datasource.login.LoginRemoteDataSource
import us.wedemy.eggeum.android.data.mapper.toModel
import us.wedemy.eggeum.android.data.mapper.toEntity
import us.wedemy.eggeum.android.domain.model.login.LoginRequestEntity
import us.wedemy.eggeum.android.domain.model.login.LoginResponseEntity
import us.wedemy.eggeum.android.domain.model.login.SignUpRequestEntity
import us.wedemy.eggeum.android.domain.model.login.SignUpResponseEntity
import us.wedemy.eggeum.android.domain.repository.LoginRepository

@Singleton
internal class LoginRepositoryImpl @Inject constructor(
  private val remoteDataSource: LoginRemoteDataSource,
  private val localDataSource: LoginLocalDataSource,
) : LoginRepository {
  override suspend fun login(loginRequestEntity: LoginRequestEntity): LoginResponseEntity {
    return remoteDataSource.login(loginRequestEntity.toModel()).toEntity()
  }

  override suspend fun signUp(
    signUpRequestEntity: SignUpRequestEntity,
  ): SignUpResponseEntity {
    return remoteDataSource.signUp(signUpRequestEntity.toModel()).toEntity()
  }

  override suspend fun setAccessToken(accessToken: String) {
    localDataSource.setAccessToken(accessToken)
  }

  override suspend fun setRefreshToken(refreshToken: String) {
    localDataSource.setRefreshToken(refreshToken)
  }

  override suspend fun getAccessToken(): String {
    return localDataSource.getAccessToken()
  }

  override suspend fun getRefreshToken(): String {
    return localDataSource.getRefreshToken()
  }

  override suspend fun deleteAuthToken() {
    localDataSource.deleteAuthToken()
  }
}
