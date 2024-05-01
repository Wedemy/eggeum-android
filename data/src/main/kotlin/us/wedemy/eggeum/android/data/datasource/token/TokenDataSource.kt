/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.token

import us.wedemy.eggeum.android.data.model.token.TokenRequest
import us.wedemy.eggeum.android.data.model.token.TokenResponse

internal interface TokenDataSource {
  suspend fun setAccessToken(accessToken: String)

  suspend fun setRefreshToken(refreshToken: String)

  suspend fun getAccessToken(): String

  suspend fun getRefreshToken(): String

  suspend fun clear()

  suspend fun getNewAccessToken(tokenRequest: TokenRequest): Result<TokenResponse>
}
