/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.user

import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.data.model.user.UpdateUserInfoRequest
import us.wedemy.eggeum.android.data.model.user.UpdateUserNicknameRequest
import us.wedemy.eggeum.android.data.model.user.UserInfoResponse
import us.wedemy.eggeum.android.data.service.UserService

@Singleton
internal class UserDataSourceImpl @Inject constructor(
  private val service: UserService,
) : UserDataSource {
  override suspend fun getUserInfo(): UserInfoResponse {
    return service.getUserInfo()
  }

  override suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest) {
    service.updateUserInfo(updateUserInfoRequest)
  }

  override suspend fun withdraw() {
    service.withdraw()
  }

  override suspend fun updateUserNickname(updateUserNicknameRequest: UpdateUserNicknameRequest) {
    service.updateUserNickname(updateUserNicknameRequest)
  }

  override suspend fun checkNicknameExist(nickname: String): Boolean {
    return service.checkNicknameExist(nickname)
  }
}
