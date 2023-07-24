/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.data.client.jsonBody
import us.wedemy.eggeum.android.data.mapper.toDomain
import us.wedemy.eggeum.android.data.model.login.LoginBodyResponse
import us.wedemy.eggeum.android.data.model.login.SignUpBodyResponse
import us.wedemy.eggeum.android.domain.model.login.LoginBody
import us.wedemy.eggeum.android.domain.model.login.SignUpBody
import us.wedemy.eggeum.android.domain.repository.LoginRepository

@Singleton
public class LoginRepositoryProvider @Inject constructor(
  private val client: HttpClient,
  moshi: Moshi,
) : LoginRepository {
  private val loginBodyAdapter = moshi.adapter<LoginBodyResponse>()
  private val signUpBodyAdapter = moshi.adapter<SignUpBodyResponse>()

  override suspend fun getLoginBody(idToken: String?): LoginBody? {
    val responseText = client.post("app/sns-sign-in") {
      jsonBody(true) {
        "idToken" withString idToken
      }
    }.bodyAsText()
    val response = loginBodyAdapter.fromJson(responseText)
    return response?.toDomain()
  }

  override suspend fun getSignUpBody(
    agreemMarketing: Boolean?,
    idToken: String?,
    nickname: String?,
  ): SignUpBody? {
    val responseText = client.post("app/sns-sign-up") {
      jsonBody(true) {
        "agreemMarketing" withBoolean agreemMarketing
        "idToken" withString nickname
        "nickname" withString nickname
      }
    }.bodyAsText()
    val response = signUpBodyAdapter.fromJson(responseText)
    return response?.toDomain()
  }
}