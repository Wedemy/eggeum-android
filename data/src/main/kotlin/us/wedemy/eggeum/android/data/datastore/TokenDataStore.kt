/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datastore

import kotlinx.coroutines.flow.Flow

internal interface TokenDataStore {
  suspend fun setAccessToken(accessToken: String)

  suspend fun setRefreshToken(refreshToken: String)

  fun getAccessToken(): Flow<String>

  fun getRefreshToken(): Flow<String>

  suspend fun clearDataStore()
}
