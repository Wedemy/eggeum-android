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

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import us.wedemy.eggeum.android.data.model.token.TokenRequest
import us.wedemy.eggeum.android.data.model.token.TokenResponse
import us.wedemy.eggeum.android.data.service.TokenService
import us.wedemy.eggeum.android.domain.util.runSuspendCatching

internal class TokenDataSourceImpl @Inject constructor(
  private val dataStore: DataStore<Preferences>,
  private val service: TokenService,
) : TokenDataSource {
  private companion object {
    private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
  }

  override suspend fun setAccessToken(accessToken: String) {
    dataStore.edit { preferences -> preferences[KEY_ACCESS_TOKEN] = accessToken }
  }

  override suspend fun setRefreshToken(refreshToken: String) {
    dataStore.edit { preferences -> preferences[KEY_REFRESH_TOKEN] = refreshToken }
  }

  override suspend fun getAccessToken(): String = dataStore.data
    .catch { exception ->
      if (exception is IOException) emit(emptyPreferences())
      else throw exception
    }.first()[KEY_ACCESS_TOKEN] ?: ""

  override suspend fun getRefreshToken(): String = dataStore.data
    .catch { exception ->
      if (exception is IOException) emit(emptyPreferences())
      else throw exception
    }.first()[KEY_REFRESH_TOKEN] ?: ""

  override suspend fun clear() {
    dataStore.edit { it.clear() }
  }

  override suspend fun getNewAccessToken(tokenRequest: TokenRequest): Result<TokenResponse> =
    runSuspendCatching {
      service.getRefreshToken(tokenRequest)
    }
}
