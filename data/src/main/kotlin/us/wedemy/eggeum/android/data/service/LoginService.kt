/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.service

import retrofit2.http.Body
import retrofit2.http.POST
import us.wedemy.eggeum.android.data.model.login.LoginRequest
import us.wedemy.eggeum.android.data.model.login.LoginResponse
import us.wedemy.eggeum.android.data.model.login.SignUpRequest
import us.wedemy.eggeum.android.data.model.login.SignUpResponse

internal interface LoginService {

  @POST("app/sns-sign-in")
  suspend fun login(
    @Body loginRequest: LoginRequest,
  ): LoginResponse

  @POST("app/sns-sign-up")
  suspend fun signUp(
    @Body signUpRequest: SignUpRequest,
  ): SignUpResponse
}
