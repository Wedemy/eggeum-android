/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.usecase

import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.domain.model.login.LoginBody
import us.wedemy.eggeum.android.domain.model.login.SignUpBody
import us.wedemy.eggeum.android.domain.repository.LoginRepository
import us.wedemy.eggeum.android.domain.util.runSuspendCatching

private val LoginApiResponseIsNull = IOException("The Login API response is null.")

@Singleton
public class GetLoginBodyUseCase @Inject constructor(
  private val repository: LoginRepository,
) {
  public suspend fun execute(idToken: String? = null): Result<LoginBody> =
    runSuspendCatching {
      repository.getLoginBody(idToken) ?: throw LoginApiResponseIsNull
    }
}

@Singleton
public class GetSignUpBodyUseCase @Inject constructor(
  private val repository: LoginRepository,
) {
  public suspend fun execute(
    agreemMarketing: Boolean? = null,
    idToken: String? = null,
    nickname: String? = null,
  ): Result<SignUpBody> =
    runSuspendCatching {
      repository.getSignUpBody(
        agreemMarketing = agreemMarketing,
        idToken = idToken,
        nickname = nickname,
      ) ?: throw LoginApiResponseIsNull
    }
}
