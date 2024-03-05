/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.service

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query
import us.wedemy.eggeum.android.data.model.user.UpdateUserInfoRequest
import us.wedemy.eggeum.android.data.model.user.UserInfoResponse

public interface UserService {
  @GET("app/users/me")
  public suspend fun getUserInfo(): UserInfoResponse

  @PUT("app/users/me")
  public suspend fun updateUserInfo(
    @Body updateUserInfoRequest: UpdateUserInfoRequest,
  )

  @DELETE("app/users/me")
  public suspend fun withdraw()

  @PUT("app/users/me/nickname")
  public suspend fun updateUserNickname(
    @Body nickname: String,
  )

  @GET("app/users/nickname/exists")
  public suspend fun checkNicknameExist(
    @Query("nickname") nickname: String,
  ): Boolean
}
