package us.wedemy.eggeum.android.data.service

import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import us.wedemy.eggeum.android.data.datasource.token.TokenDataSource
import us.wedemy.eggeum.android.data.datastore.TokenDataStoreProvider
import us.wedemy.eggeum.android.data.model.token.TokenRequest

@Suppress("TooGenericExceptionCaught")
public class TokenAuthenticator @Inject constructor(
  private val dataStoreProvider: TokenDataStoreProvider,
  private val tokenDataSource: TokenDataSource,
) : Authenticator {
  override fun authenticate(route: Route?, response: Response): Request? {
    Timber.d("authenticate 호출")
    return runBlocking {
      try {
        val refreshToken = dataStoreProvider.getRefreshToken()
        val tokenResponse = tokenDataSource.getRefreshToken(TokenRequest(refreshToken))
        if (tokenResponse != null) {
          dataStoreProvider.setAccessToken(tokenResponse.accessToken)
          dataStoreProvider.setRefreshToken(tokenResponse.refreshToken)
          newRequestWithAccessToken(response.request, tokenResponse.accessToken)
        } else {
          null
        }
      } catch (e: Exception) {
        Timber.e("TokenAuthenticator Error :: ${e.message}")
        null
      }
    }
  }

  private fun newRequestWithAccessToken(request: Request, accessToken: String): Request =
    request.newBuilder()
      .header("Authorization", "Bearer $accessToken")
      .build()
}
