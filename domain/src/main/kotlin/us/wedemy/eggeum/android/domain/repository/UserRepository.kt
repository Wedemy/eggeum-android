/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.repository

import us.wedemy.eggeum.android.domain.model.user.UpdateUserInfoBody
import us.wedemy.eggeum.android.domain.model.user.UserInfoBody

/** 사용자 API */
public interface UserRepository {
  /**
   * 내 정보 조회
   */
  public suspend fun getUserInfo(): UserInfoBody?

  /**
   * 내 정보 수정
   *
   * @param updateUserInfoBody
   */
  public suspend fun updateUserInfo(updateUserInfoBody: UpdateUserInfoBody)

  /**
   * 회원 탈퇴
   *
   */
  public suspend fun withdraw()

  /**
   * 닉네임 수정
   *
   * @param nickname 닉네임
   */
  public suspend fun updateUserNickname(nickname: String)

  /**
   * 닉네임 존재여부 조회
   *
   * @param nickname 닉네임
   */
  public suspend fun checkNicknameExist(nickname: String): Boolean?
}
