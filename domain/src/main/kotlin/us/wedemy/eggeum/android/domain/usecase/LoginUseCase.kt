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
import us.wedemy.eggeum.android.domain.model.login.LoginRequestEntity
import us.wedemy.eggeum.android.domain.model.login.LoginResponseEntity
import us.wedemy.eggeum.android.domain.model.login.SignUpRequestEntity
import us.wedemy.eggeum.android.domain.model.login.SignUpResponseEntity
import us.wedemy.eggeum.android.domain.repository.LoginRepository
import us.wedemy.eggeum.android.domain.util.runSuspendCatching

private val LoginApiResponseIsNull = IOException("Login API response is null.")
private val SignUpApiResponseIsNull = IOException("SignUp API response is null.")

@Singleton
public class LoginUseCase @Inject constructor(
  private val repository: LoginRepository,
) {
  public suspend operator fun invoke(
    loginRequestEntity: LoginRequestEntity
  ): Result<LoginResponseEntity> =
    runSuspendCatching {
      repository.login(loginRequestEntity) ?: throw LoginApiResponseIsNull
    }
}

@Singleton
public class SignUpUseCase @Inject constructor(
  private val repository: LoginRepository,
) {
  public suspend operator fun invoke(
    signUpRequestEntity: SignUpRequestEntity,
  ): Result<SignUpResponseEntity> =
    runSuspendCatching {
      repository.signUp(signUpRequestEntity) ?: throw SignUpApiResponseIsNull
    }
}

@Singleton
public class SetAccessTokenUseCase @Inject constructor(
  private val repository: LoginRepository,
) {
  public suspend operator fun invoke(accessToken: String) {
    repository.setAccessToken(accessToken)
  }
}

@Singleton
public class SetRefreshTokenUseCase @Inject constructor(
  private val repository: LoginRepository,
) {
  public suspend operator fun invoke(refreshToken: String) {
    repository.setRefreshToken(refreshToken)
  }
}

@Singleton
public class GetAccessTokenUseCase @Inject constructor(
  private val repository: LoginRepository,
) {
  public suspend operator fun invoke(): String {
    return repository.getAccessToken()
  }
}

@Singleton
public class GetRefreshTokenUseCase @Inject constructor(
  private val repository: LoginRepository,
) {
  public suspend operator fun invoke(): String {
    return repository.getRefreshToken()
  }
}

@Singleton
public class LogoutUseCase @Inject constructor(
  private val repository: LoginRepository,
) {
  public suspend operator fun invoke() {
    repository.deleteAuthToken()
  }
}
