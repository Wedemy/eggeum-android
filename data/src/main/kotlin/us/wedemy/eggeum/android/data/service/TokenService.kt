/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.service

import retrofit2.http.Body
import retrofit2.http.POST
import us.wedemy.eggeum.android.data.model.token.TokenRequest
import us.wedemy.eggeum.android.data.model.token.TokenResponse

internal interface TokenService {

  @POST("app/token/refresh")
  suspend fun refresh(
    @Body tokenRequest: TokenRequest,
  ): TokenResponse
}
