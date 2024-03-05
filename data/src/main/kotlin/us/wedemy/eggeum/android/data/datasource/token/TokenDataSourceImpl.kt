/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.token

import javax.inject.Inject
import us.wedemy.eggeum.android.data.model.token.TokenRequest
import us.wedemy.eggeum.android.data.model.token.TokenResponse
import us.wedemy.eggeum.android.data.service.TokenService
import us.wedemy.eggeum.android.domain.util.runSuspendCatching

public class TokenDataSourceImpl @Inject constructor(
  private val service: TokenService,
) : TokenDataSource {

  override suspend fun getRefreshToken(tokenRequest: TokenRequest): Result<TokenResponse> =
    runSuspendCatching {
      service.getRefreshToken(tokenRequest)
    }
}
