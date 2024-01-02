package us.wedemy.eggeum.android.data.service

import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import us.wedemy.eggeum.android.data.datastore.TokenDataStoreProvider

public class TokenAuthenticator @Inject constructor(
  private val dataStoreProvider: TokenDataStoreProvider,
) : Authenticator {

  override fun authenticate(route: Route?, response: Response): Request {
    synchronized(this) {
      val newAccessToken = runBlocking { dataStoreProvider.getRefreshToken() }
      return newRequestWithAccessToken(response.request, newAccessToken)
    }
  }

  private fun newRequestWithAccessToken(request: Request, accessToken: String): Request =
    request.newBuilder()
      .addHeader("Content-Type", "application/json")
      .addHeader("Authorization", "Bearer $accessToken")
      .build()
}
