package us.wedemy.eggeum.android.data.service

import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.HttpException
import us.wedemy.eggeum.android.data.datastore.TokenDataStoreProvider
import us.wedemy.eggeum.android.domain.util.RefreshTokenExpiredException

@Suppress("TooGenericExceptionCaught")
public class TokenAuthenticator @Inject constructor(
  private val dataStoreProvider: TokenDataStoreProvider,
) : Authenticator {

  override fun authenticate(route: Route?, response: Response): Request {
    synchronized(this) {
      try {
        val newAccessToken = runBlocking { dataStoreProvider.getRefreshToken() }
        return newRequestWithAccessToken(response.request, newAccessToken)
      } catch (e: Exception) {
        when(e) {
          is HttpException -> {
            if (e.code() == 401) {
              throw RefreshTokenExpiredException
            } else {
              throw e
            }
          }
          else -> throw e
        }
      }
    }
  }

  private fun newRequestWithAccessToken(request: Request, accessToken: String): Request =
    request.newBuilder()
      .addHeader("Content-Type", "application/json")
      .addHeader("Authorization", "Bearer $accessToken")
      .build()
}
