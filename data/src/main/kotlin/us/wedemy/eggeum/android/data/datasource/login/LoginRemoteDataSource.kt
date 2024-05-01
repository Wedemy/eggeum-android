/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.login

import us.wedemy.eggeum.android.data.model.login.LoginRequest
import us.wedemy.eggeum.android.data.model.login.LoginResponse
import us.wedemy.eggeum.android.data.model.login.SignUpRequest
import us.wedemy.eggeum.android.data.model.login.SignUpResponse

internal interface LoginRemoteDataSource {
  suspend fun login(loginRequest: LoginRequest): LoginResponse

  suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse
}
