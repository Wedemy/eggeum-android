package us.wedemy.eggeum.android.data.service

import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import us.wedemy.eggeum.android.common.util.RefreshTokenExpiredException
import us.wedemy.eggeum.android.data.datasource.token.TokenDataSource
import us.wedemy.eggeum.android.data.model.token.TokenRequest

internal class TokenAuthenticator @Inject constructor(
  private val tokenDataSource: TokenDataSource,
) : Authenticator {
  override fun authenticate(route: Route?, response: Response): Request? {
    return runBlocking {
      tokenDataSource.getNewAccessToken(TokenRequest(tokenDataSource.getRefreshToken()))
        .onSuccess { tokenResponse ->
          tokenDataSource.setAccessToken(tokenResponse.accessToken)
          tokenDataSource.setRefreshToken(tokenResponse.refreshToken)
        }.map { tokenResponse ->
          newRequestWithAccessToken(response.request, tokenResponse.accessToken)
        }.onFailure {
          throw RefreshTokenExpiredException
        }.getOrNull()
    }
  }

  private fun newRequestWithAccessToken(request: Request, accessToken: String): Request =
    request.newBuilder()
      .header("Authorization", "Bearer $accessToken")
      .build()
}
