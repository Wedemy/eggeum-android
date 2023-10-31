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
import us.wedemy.eggeum.android.data.model.user.UserInfoResponse
import us.wedemy.eggeum.android.data.service.UserService
import us.wedemy.eggeum.android.data.util.safeRequest

@Singleton
public class UserDataSourceImpl @Inject constructor(
  private val service: UserService,
) : UserDataSource {
  override suspend fun getUserInfo(): UserInfoResponse? {
    return safeRequest {
      service.getUserInfo()
    }
  }

  override suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest) {
    safeRequest {
      service.updateUserInfo(updateUserInfoRequest)
    }
  }

  override suspend fun withdraw() {
    safeRequest {
      service.withdraw()
    }
  }

  override suspend fun updateUserNickname(nickname: String) {
    safeRequest {
      service.updateUserNickname(nickname)
    }
  }

  override suspend fun checkNicknameExist(nickname: String): Boolean? {
    return safeRequest {
      service.checkNicknameExist(nickname)
    }
  }
}
