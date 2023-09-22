/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.data.datasource.LoginLocalDataSource
import us.wedemy.eggeum.android.data.datasource.LoginRemoteDataSource
import us.wedemy.eggeum.android.domain.model.login.LoginBody
import us.wedemy.eggeum.android.domain.model.login.SignUpBody
import us.wedemy.eggeum.android.domain.repository.LoginRepository

@Singleton
public class LoginRepositoryProvider @Inject constructor(
  private val remoteDataSource: LoginRemoteDataSource,
  private val localDataSource: LoginLocalDataSource,
) : LoginRepository {
  override suspend fun getLoginBody(idToken: String?): LoginBody? {
    return remoteDataSource.getLoginBody(idToken)
  }

  override suspend fun getSignUpBody(
    agreemMarketing: Boolean?,
    idToken: String?,
    nickname: String?,
  ): SignUpBody? {
    return remoteDataSource.getSignUpBody(
      agreemMarketing = agreemMarketing,
      idToken = idToken,
      nickname = nickname,
    )
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
