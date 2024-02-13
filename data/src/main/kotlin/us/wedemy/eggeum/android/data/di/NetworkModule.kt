/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import us.wedemy.eggeum.android.data.BuildConfig
import us.wedemy.eggeum.android.data.datasource.token.TokenDataSource
import us.wedemy.eggeum.android.data.datastore.TokenDataStoreProvider
import us.wedemy.eggeum.android.data.service.TokenAuthenticator
import us.wedemy.eggeum.android.data.service.TokenInterceptor
import us.wedemy.eggeum.android.data.util.JsonBuilder
import us.wedemy.eggeum.android.data.util.buildJson

private const val MaxTimeoutMillis = 30_000L
private const val MaxRetryCount = 3

private val jsonRule = Json {
  encodeDefaults = true
  ignoreUnknownKeys = true
  prettyPrint = true
  isLenient = true
}

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

  @Singleton
  @Named("KtorAuthHttpClient")
  @Provides
  internal fun provideKtorHttpClient(): HttpClient {
    return HttpClient(engineFactory = CIO) {
      engine {
        endpoint {
          connectTimeout = MaxTimeoutMillis
          connectAttempts = MaxRetryCount
        }
      }
      defaultRequest {
        url(BuildConfig.SERVER_BASE_URL)
        contentType(ContentType.Application.Json)
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
  @Named("KtorHttpClient")
  @Provides
  internal fun provideKtorApiHttpClient(dataStoreProvider: TokenDataStoreProvider): HttpClient {
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

  @Singleton
  @Provides
  internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor { message ->
      Timber.tag("httpClient").d(message)
    }.apply {
      level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
      } else {
        HttpLoggingInterceptor.Level.NONE
      }
    }
  }

  @Singleton
  @Provides
  internal fun provideTokenAuthenticator(
    dataStoreProvider: TokenDataStoreProvider,
    tokenDataSource: TokenDataSource,
  ): TokenAuthenticator {
    return TokenAuthenticator(dataStoreProvider, tokenDataSource)
  }

  @Singleton
  @Provides
  internal fun provideTokenInterceptor(
    dataStoreProvider: TokenDataStoreProvider,
  ): TokenInterceptor {
    return TokenInterceptor(dataStoreProvider)
  }

  @Singleton
  @Provides
  @Named("RetrofitAuthHttpClient")
  internal fun provideRetrofitAuthHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
  ): Retrofit {
    val contentType = "application/json".toMediaType()
    val httpClient = OkHttpClient.Builder()
      .connectTimeout(MaxTimeoutMillis, TimeUnit.MILLISECONDS)
      .addInterceptor(httpLoggingInterceptor)
      .build()

    return Retrofit.Builder()
      .baseUrl(BuildConfig.SERVER_BASE_URL)
      .client(httpClient)
      .addConverterFactory(jsonRule.asConverterFactory(contentType))
      .build()
  }

  @Singleton
  @Provides
  @Named("RetrofitHttpClient")
  internal fun provideRetrofitHttpClient(
    tokenInterceptor: TokenInterceptor,
    tokenAuthenticator: TokenAuthenticator,
    httpLoggingInterceptor: HttpLoggingInterceptor,
  ): Retrofit {
    val contentType = "application/json".toMediaType()
    val httpClient = OkHttpClient.Builder()
      .connectTimeout(MaxTimeoutMillis, TimeUnit.MILLISECONDS)
      // To set the token in the header
      .addInterceptor(tokenInterceptor)
      // To update the token when it gets HTTP unauthorized error
      .authenticator(tokenAuthenticator)
      .addInterceptor(httpLoggingInterceptor)
      .build()

    return Retrofit.Builder()
      .baseUrl(BuildConfig.SERVER_BASE_URL)
      .client(httpClient)
      .addConverterFactory(jsonRule.asConverterFactory(contentType))
      .build()
  }

  @Singleton
  @Provides
  @Named("RetrofitFileHttpClient")
  internal fun provideRetrofitFileHttpClient(
    tokenAuthenticator: TokenAuthenticator,
    tokenInterceptor: TokenInterceptor,
    httpLoggingInterceptor: HttpLoggingInterceptor,
  ): Retrofit {
    val contentType = "multipart/form-data".toMediaType()
    val httpClient = OkHttpClient.Builder()
      .connectTimeout(MaxTimeoutMillis, TimeUnit.MILLISECONDS)
      .readTimeout(MaxTimeoutMillis, TimeUnit.MILLISECONDS)
      .writeTimeout(MaxTimeoutMillis, TimeUnit.MILLISECONDS)
      // To set the token in the header
      .addInterceptor(tokenInterceptor)
      // To update the token when it gets HTTP unauthorized error
      .authenticator(tokenAuthenticator)
      .addInterceptor(httpLoggingInterceptor)
      .build()

    return Retrofit.Builder()
      .baseUrl(BuildConfig.SERVER_BASE_URL)
      .client(httpClient)
      .addConverterFactory(jsonRule.asConverterFactory(contentType))
      .build()
  }

  @Suppress("unused")
  internal fun HttpRequestBuilder.jsonBody(pretty: Boolean = true, builder: JsonBuilder.() -> Unit) {
    setBody(buildJson(pretty, builder))
  }
}
