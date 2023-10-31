/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.user

import us.wedemy.eggeum.android.data.model.user.UpdateUserInfoRequest
import us.wedemy.eggeum.android.data.model.user.UserInfoResponse

public interface UserDataSource {
  public suspend fun getUserInfo(): UserInfoResponse?

  public suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest)

  public suspend fun withdraw()

  public suspend fun updateUserNickname(nickname: String)

  public suspend fun checkNicknameExist(nickname: String): Boolean?
}
