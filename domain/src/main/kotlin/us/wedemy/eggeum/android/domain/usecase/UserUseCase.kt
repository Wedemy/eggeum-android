/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.domain.model.user.UpdateUserInfoEntity
import us.wedemy.eggeum.android.domain.model.user.UserInfoEntity
import us.wedemy.eggeum.android.domain.repository.UserRepository
import us.wedemy.eggeum.android.domain.util.runSuspendCatching

@Singleton
public class GetUserInfoUseCase @Inject constructor(
  private val repository: UserRepository,
) {
  public suspend operator fun invoke(): Result<UserInfoEntity> =
    runSuspendCatching {
      repository.getUserInfo()
    }
}

@Singleton
public class UpdateUserInfoUseCase @Inject constructor(
  private val repository: UserRepository,
) {
  public suspend operator fun invoke(updateUserInfoEntity: UpdateUserInfoEntity): Result<Unit> =
    runSuspendCatching {
      repository.updateUserInfo(updateUserInfoEntity)
    }
}

@Singleton
public class WithdrawUseCase @Inject constructor(
  private val repository: UserRepository,
) {
  public suspend operator fun invoke(): Result<Unit> =
    runSuspendCatching {
      repository.withdraw()
    }
}

@Singleton
public class UpdateUserNicknameUseCase @Inject constructor(
  private val repository: UserRepository,
) {
  public suspend operator fun invoke(nickname: String): Result<Unit> =
    runSuspendCatching {
      repository.updateUserNickname(nickname)
    }
}

@Singleton
public class CheckNicknameExistUseCase @Inject constructor(
  private val repository: UserRepository,
) {
  public suspend operator fun invoke(nickname: String): Result<Boolean> =
    runSuspendCatching {
      repository.checkNicknameExist(nickname)
    }
}
