/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data

import com.fasterxml.jackson.databind.DeserializationFeature
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import java.util.Locale
import java.util.TimeZone
import timber.log.Timber

private const val MaxTimeoutMillis = 3000L
private const val MaxRetryCount = 3

private val KtorClient =
  HttpClient(engineFactory = CIO) {
    engine {
      endpoint {
        connectTimeout = MaxTimeoutMillis
        connectAttempts = MaxRetryCount
      }
    }
    defaultRequest {
      // TODO: default url 설정
      contentType(ContentType.Application.Json)
    }
    install(ContentNegotiation) {
      jackson {
        disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        setTimeZone(TimeZone.getTimeZone("Asia/Seoul"))
        setLocale(Locale.KOREA)
      }
    }
    install(Logging) {
      logger = object : Logger {
        override fun log(message: String) {
          Timber.d(message)
        }
      }
      level = LogLevel.ALL
    }
  }

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
object HttpClientProvider {
  @Provides
  fun provideKtorClient() = KtorClient
}
