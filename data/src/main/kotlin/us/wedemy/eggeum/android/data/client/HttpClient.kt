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
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import us.wedemy.eggeum.android.data.BuildConfig
import us.wedemy.eggeum.android.data.datastore.TokenDataStoreProvider

private const val MaxTimeoutMillis = 3000L
private const val MaxRetryCount = 3

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

  @Singleton
  @Named("HttpClient")
  @Provides
  internal fun provideHttpClient(dataStoreProvider: TokenDataStoreProvider): HttpClient {
    return HttpClient(engineFactory = CIO) {
      engine {
        endpoint {
          connectTimeout = MaxTimeoutMillis
          connectAttempts = MaxRetryCount
        }
      }
      defaultRequest {
        val accessToken = runBlocking {
          dataStoreProvider.getAccessToken()
        }
        url(BuildConfig.SERVER_BASE_URL)
        contentType(ContentType.Application.Json)
        header("AccessToken", accessToken)
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
  }

  @Singleton
  @Named("AuthHttpClient")
  @Provides
  internal fun provideApiHttpClient(dataStoreProvider: TokenDataStoreProvider): HttpClient {
    return HttpClient(engineFactory = CIO) {
      engine {
        endpoint {
          connectTimeout = MaxTimeoutMillis
          connectAttempts = MaxRetryCount
        }
      }
      defaultRequest {
        val accessToken = runBlocking {
          dataStoreProvider.getAccessToken()
        }
        url(BuildConfig.SERVER_BASE_URL)
        contentType(ContentType.Application.Json)
        header("Authorization", "Bearer $accessToken")
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
  }
}

internal fun HttpRequestBuilder.jsonBody(pretty: Boolean = true, builder: JsonBuilder.() -> Unit) {
  setBody(buildJson(pretty, builder))
}
