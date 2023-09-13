/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.repository

import us.wedemy.eggeum.android.domain.model.login.LoginBody
import us.wedemy.eggeum.android.domain.model.login.SignUpBody

/** 로그인 API */
public interface LoginRepository {
  /** 로그인 API
   *
   * @param idToken 사용자 인증 토큰
   */
  public suspend fun getLoginBody(idToken: String?): LoginBody?

  /** 회원가입 API
   *
   * @param agreemMarketing 마케팅 동의 여부
   * @param idToken 사용자 인증 토큰
   * @param nickname 유저에 앱에서 사용할 닉네임
   */
  public suspend fun getSignUpBody(
    agreemMarketing: Boolean?,
    idToken: String?,
    nickname: String?,
  ): SignUpBody?

  /** AccessToken 저장
   *
   * @param accessToken 엑세스 토큰
   */
  public suspend fun setAccessToken(accessToken: String)


  /** RefreshToken 저장
   *
   * @param refreshToken 리프레시 토큰
   */
  public suspend fun setRefreshToken(refreshToken: String)

  /** AccessToken 조회*/
  public suspend fun getAccessToken(): String

  /** RefreshToken 조회*/
  public suspend fun getRefreshToken(): String

  /** 로그인 토큰 초기화*/
  public suspend fun clearLoginToken()
}
