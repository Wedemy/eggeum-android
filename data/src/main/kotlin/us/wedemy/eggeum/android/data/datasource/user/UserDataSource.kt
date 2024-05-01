/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.user

import us.wedemy.eggeum.android.data.model.user.UpdateUserInfoRequest
import us.wedemy.eggeum.android.data.model.user.UserInfoResponse

internal interface UserDataSource {
  suspend fun getUserInfo(): UserInfoResponse

  suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest)

  suspend fun withdraw()

  suspend fun updateUserNickname(nickname: String)

  suspend fun checkNicknameExist(nickname: String): Boolean
}
