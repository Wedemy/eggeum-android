/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.repository

import us.wedemy.eggeum.android.domain.model.user.UpdateUserInfoEntity
import us.wedemy.eggeum.android.domain.model.user.UpdateUserNicknameEntity
import us.wedemy.eggeum.android.domain.model.user.UserInfoEntity

/** 사용자 API */
public interface UserRepository {
  /**
   * 내 정보 조회
   */
  public suspend fun getUserInfo(): UserInfoEntity

  /**
   * 내 정보 수정
   *
   * @param updateUserInfoEntity
   */
  public suspend fun updateUserInfo(updateUserInfoEntity: UpdateUserInfoEntity)

  /**
   * 회원 탈퇴
   *
   */
  public suspend fun withdraw()

  /**
   * 닉네임 수정
   *
   * @param updateUserNicknameEntity
   */
  public suspend fun updateUserNickname(updateUserNicknameEntity: UpdateUserNicknameEntity)

  /**
   * 닉네임 존재여부 조회
   *
   * @param nickname 닉네임
   */
  public suspend fun checkNicknameExist(nickname: String): Boolean
}
