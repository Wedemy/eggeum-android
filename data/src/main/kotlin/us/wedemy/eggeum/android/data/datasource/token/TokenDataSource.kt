/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.token

import us.wedemy.eggeum.android.data.model.token.TokenRequest
import us.wedemy.eggeum.android.data.model.token.TokenResponse

public interface TokenDataSource {
  public suspend fun getRefreshToken(tokenRequest: TokenRequest): TokenResponse?
}
