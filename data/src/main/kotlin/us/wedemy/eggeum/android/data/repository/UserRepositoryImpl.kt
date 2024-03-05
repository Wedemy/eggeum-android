/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.data.datasource.user.UserDataSource
import us.wedemy.eggeum.android.data.mapper.toEntity
import us.wedemy.eggeum.android.data.mapper.toModel
import us.wedemy.eggeum.android.domain.model.user.UpdateUserInfoEntity
import us.wedemy.eggeum.android.domain.model.user.UserInfoEntity
import us.wedemy.eggeum.android.domain.repository.UserRepository

@Singleton
public class UserRepositoryImpl @Inject constructor(
  private val dataSource: UserDataSource,
) : UserRepository {
  override suspend fun getUserInfo(): UserInfoEntity {
    return dataSource.getUserInfo().toEntity()
  }

  override suspend fun updateUserInfo(updateUserInfoEntity: UpdateUserInfoEntity) {
    dataSource.updateUserInfo(updateUserInfoEntity.toModel())
  }

  override suspend fun withdraw() {
    dataSource.withdraw()
  }

  override suspend fun updateUserNickname(nickname: String) {
    dataSource.updateUserNickname(nickname)
  }

  override suspend fun checkNicknameExist(nickname: String): Boolean {
    return dataSource.checkNicknameExist(nickname)
  }
}
