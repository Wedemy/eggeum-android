package us.wedemy.eggeum.android.data.service

import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import us.wedemy.eggeum.android.data.datasource.token.TokenDataSource

internal class TokenAuthenticator @Inject constructor(
  private val tokenDataSource: TokenDataSource,
) : Authenticator {
  override fun authenticate(route: Route?, response: Response): Request? {
    val newAccessToken = runBlocking { getNewAccessToken() } ?: return null
    return newRequestWithAccessToken(response.request, newAccessToken)
  }

//  override fun authenticate(route: Route?, response: Response): Request? {
//    return runBlocking {
//      tokenDataSource.getNewAccessToken(TokenRequest(tokenDataSource.getRefreshToken()))
//        .onSuccess { tokenResponse ->
//          tokenDataSource.setAccessToken(tokenResponse.accessToken)
//          tokenDataSource.setRefreshToken(tokenResponse.refreshToken)
//        }.map { tokenResponse ->
//          newRequestWithAccessToken(response.request, tokenResponse.accessToken)
//        }.onFailure {
//          throw RefreshTokenExpiredException
//        }.getOrNull()
//    }
//  }

  private suspend fun getNewAccessToken(): String? {
    val response = tokenDataSource.refresh()
    val newToken = response.getOrNull()
    return newToken?.let {
      tokenDataSource.apply {
        setAccessToken(it.accessToken)
        setRefreshToken(it.refreshToken)
      }
      it.accessToken
    } ?: run {
      tokenDataSource.clear()
      null
    }
  }

  private fun newRequestWithAccessToken(request: Request, accessToken: String): Request =
    request.newBuilder()
      .header("Authorization", "Bearer $accessToken")
      .build()
}
