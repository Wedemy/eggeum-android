/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datastore

public interface TokenDataStore {
  public suspend fun setAccessToken(accessToken: String)

  public suspend fun setRefreshToken(refreshToken: String)

  public suspend fun getAccessToken(): String

  public suspend fun getRefreshToken(): String

  public suspend fun clear()
}
