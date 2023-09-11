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
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.put
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.data.client.jsonBody
import us.wedemy.eggeum.android.data.mapper.toData
import us.wedemy.eggeum.android.data.mapper.toDomain
import us.wedemy.eggeum.android.data.model.ProfileImage
import us.wedemy.eggeum.android.data.model.user.UserInfoBodyResponse
import us.wedemy.eggeum.android.domain.model.user.UpdateUserInfoBody
import us.wedemy.eggeum.android.domain.model.user.UserInfoBody
import us.wedemy.eggeum.android.domain.repository.UserRepository

@Singleton
public class UserRepositoryProvider @Inject constructor(
  private val client: HttpClient,
  moshi: Moshi,
) : UserRepository {
  private val userInfoBodayAdapter = moshi.adapter<UserInfoBodyResponse>()
  private val profileImageAdapter = moshi.adapter<ProfileImage>()
  override suspend fun getUserInfo(): UserInfoBody? {
    val responseText =
      client
        .get("app/users/me")
        .bodyAsText()
    val response = userInfoBodayAdapter.fromJson(responseText)
    return response?.toDomain()
  }

  override suspend fun updateUserInfo(updateUserInfoBody: UpdateUserInfoBody) {
    client
      .put("app/users/me") {
        jsonBody {
          "nickname" withString updateUserInfoBody.nickname
          "profileImage".withPojo(profileImageAdapter, updateUserInfoBody.profileImage.toData())
        }
      }
  }

  override suspend fun withdraw() {
    client
      .delete("app/users/me")
  }

  override suspend fun updateUserNickname(nickname: String) {
    client
      .put("app/users/me/nickname") {
        jsonBody {
          "nickname" withString nickname
        }
      }
  }

  override suspend fun checkNicknameExist(nickname: String): Boolean {
    val responseText =
      client
        .get("app/users/nickname/exists") {
          parameter("nickname", nickname)
        }
        .bodyAsText()
    return responseText.toBoolean()
  }
}
