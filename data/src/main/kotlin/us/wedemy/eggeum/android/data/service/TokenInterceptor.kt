package us.wedemy.eggeum.android.data.service

import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import us.wedemy.eggeum.android.data.datasource.token.TokenDataSource

internal class TokenInterceptor @Inject constructor(
  private val tokenDataSource: TokenDataSource,
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val accessToken = runBlocking {
      tokenDataSource.getAccessToken()
    }
    val request: Request = chain.request().newBuilder()
      .addHeader("Authorization", "Bearer $accessToken")
      .build()
    return chain.proceed(request)
  }
}
