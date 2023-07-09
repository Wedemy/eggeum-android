/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.client

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import timber.log.Timber
import us.wedemy.eggeum.android.data.BuildConfig

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
      url(BuildConfig.SERVER_BASEURL)
      contentType(ContentType.Application.Json)
    }
    install(Logging) {
      logger = object : Logger {
        override fun log(message: String) {
          Timber.tag("HttpClient").d(message)
        }
      }
      level = LogLevel.ALL
    }
  }

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
public object HttpClientProvider {
  @Provides
  public fun ktorClient(): HttpClient = KtorClient
}
