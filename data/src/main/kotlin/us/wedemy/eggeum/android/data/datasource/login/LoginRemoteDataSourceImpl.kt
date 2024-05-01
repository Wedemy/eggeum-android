/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.login

import javax.inject.Inject
import us.wedemy.eggeum.android.data.model.login.LoginRequest
import us.wedemy.eggeum.android.data.model.login.LoginResponse
import us.wedemy.eggeum.android.data.model.login.SignUpRequest
import us.wedemy.eggeum.android.data.model.login.SignUpResponse
import us.wedemy.eggeum.android.data.service.LoginService

internal class LoginRemoteDataSourceImpl @Inject constructor(
  private val service: LoginService,
) : LoginRemoteDataSource {
  override suspend fun login(loginRequest: LoginRequest): LoginResponse {
    return service.login(loginRequest)
  }

  override suspend fun signUp(
    signUpRequest: SignUpRequest,
  ): SignUpResponse {
    return service.signUp(signUpRequest)
  }
}
