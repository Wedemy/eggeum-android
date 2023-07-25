/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datastore

internal interface TokenDataStore {
  suspend fun setAccessToken(accessToken: String)

  suspend fun setRefreshToken(refreshToken: String)

  suspend fun getAccessToken(): String

  suspend fun getRefreshToken(): String

  suspend fun clearDataStore()
}
