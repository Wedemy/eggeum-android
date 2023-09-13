/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource

import us.wedemy.eggeum.android.domain.model.login.LoginBody
import us.wedemy.eggeum.android.domain.model.login.SignUpBody

public interface LoginRemoteDataSource {
  public suspend fun getLoginBody(idToken: String?): LoginBody?

  public suspend fun getSignUpBody(
    agreemMarketing: Boolean?,
    idToken: String?,
    nickname: String?,
  ): SignUpBody?
}
