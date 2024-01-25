package us.wedemy.eggeum.android.data.service

import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import us.wedemy.eggeum.android.data.datastore.TokenDataStoreProvider
import us.wedemy.eggeum.android.domain.util.RefreshTokenExpiredException

@Suppress("TooGenericExceptionCaught")
public class TokenAuthenticator @Inject constructor(
  private val dataStoreProvider: TokenDataStoreProvider,
) : Authenticator {
  override fun authenticate(route: Route?, response: Response): Request? {
    Timber.d("authenticate 호출")
    return runBlocking {
      val currentAccessToken = dataStoreProvider.getAccessToken()

      // 현재 액세스 토큰이 요청 헤더의 토큰과 다르면 이미 갱신된 것으로 간주
      if (response.request.header("Authorization") != "Bearer $currentAccessToken") {
        Timber.d("RefreshToken is Expired")
        // 로그인 토큰 제거(로그아웃)
        // dataStoreProvider.clear()
        throw RefreshTokenExpiredException
      }

      try {
        val newAccessToken = runBlocking { dataStoreProvider.getRefreshToken() }
        newRequestWithAccessToken(response.request, newAccessToken)
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
