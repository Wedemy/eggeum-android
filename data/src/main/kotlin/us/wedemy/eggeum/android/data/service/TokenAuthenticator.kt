package us.wedemy.eggeum.android.data.service

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import us.wedemy.eggeum.android.data.datastore.TokenDataStoreProvider

public class TokenAuthenticator @Inject constructor(
  private val dataStoreProvider: TokenDataStoreProvider,
) : Authenticator {

  override fun authenticate(route: Route?, response: Response): Request? {
    val accessToken = runBlocking {
      dataStoreProvider.getAccessToken()
    }

    if (hasNotAccessTokenOnResponse(response)) {
      synchronized(this) {
        val newAccessToken = runBlocking {
          dataStoreProvider.getAccessToken()
        }
        if (accessToken != newAccessToken) {
          return newRequestWithAccessToken(response.request, newAccessToken)
        }

        val refreshToken = runBlocking {
          dataStoreProvider.getRefreshToken()
        }
        return newRequestWithAccessToken(response.request, refreshToken)
      }
    }

    return null
  }

  private fun hasNotAccessTokenOnResponse(response: Response): Boolean =
    response.header("Authorization") == null

  private fun newRequestWithAccessToken(request: Request, accessToken: String): Request =
    request.newBuilder()
      .addHeader("Content-Type", "application/json")
      .addHeader("Authorization", "Bearer $accessToken")
      .build()
}
