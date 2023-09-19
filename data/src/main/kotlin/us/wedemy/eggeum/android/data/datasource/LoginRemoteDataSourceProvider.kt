/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import javax.inject.Inject
import javax.inject.Named
import us.wedemy.eggeum.android.data.client.jsonBody
import us.wedemy.eggeum.android.data.mapper.toDomain
import us.wedemy.eggeum.android.data.model.login.LoginBodyResponse
import us.wedemy.eggeum.android.data.model.login.SignUpBodyResponse
import us.wedemy.eggeum.android.domain.model.login.LoginBody
import us.wedemy.eggeum.android.domain.model.login.SignUpBody
import us.wedemy.eggeum.android.domain.util.LoginApiResponseNotFound
import us.wedemy.eggeum.android.domain.util.LoginApiResponseUnknownError

public class LoginRemoteDataSourceProvider @Inject constructor(
  @Named("HttpClient")
  private val client: HttpClient,
  moshi: Moshi,
) : LoginRemoteDataSource {
  private val loginBodyAdapter = moshi.adapter<LoginBodyResponse>()
  private val signUpBodyAdapter = moshi.adapter<SignUpBodyResponse>()

  public override suspend fun getLoginBody(idToken: String?): LoginBody? {
    val httpResponse =
      client
        .post("app/sns-sign-in") {
          jsonBody {
            "idToken" withString idToken
          }
        }
    when (httpResponse.status.value) {
      HttpStatusCode.OK.value -> {
        val responseText = httpResponse.bodyAsText()
        val response = loginBodyAdapter.fromJson(responseText)
        return response?.toDomain()
      }
      HttpStatusCode.NotFound.value -> {
        throw LoginApiResponseNotFound
      }
      else -> {
        throw LoginApiResponseUnknownError
      }
    }
  }

  public override suspend fun getSignUpBody(
    agreemMarketing: Boolean?,
    idToken: String?,
    nickname: String?,
  ): SignUpBody? {
    val responseText =
      client
        .post("app/sns-sign-up") {
          jsonBody {
            "agreemMarketing" withBoolean agreemMarketing
            "idToken" withString idToken
            "nickname" withString nickname
          }
        }.bodyAsText()
    val response = signUpBodyAdapter.fromJson(responseText)
    return response?.toDomain()
  }
}
