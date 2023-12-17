/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.login

import java.net.UnknownHostException
import javax.inject.Inject
import retrofit2.HttpException
import timber.log.Timber
import us.wedemy.eggeum.android.data.extensions.toAlertMessage
import us.wedemy.eggeum.android.data.model.login.LoginRequest
import us.wedemy.eggeum.android.data.model.login.LoginResponse
import us.wedemy.eggeum.android.data.model.login.SignUpRequest
import us.wedemy.eggeum.android.data.model.login.SignUpResponse
import us.wedemy.eggeum.android.data.service.LoginService
import us.wedemy.eggeum.android.data.util.ExceptionWrapper
import us.wedemy.eggeum.android.data.util.safeRequest
import us.wedemy.eggeum.android.domain.util.LoginApiResponseNotFound

@Suppress("TooGenericExceptionCaught")
public class LoginRemoteDataSourceImpl @Inject constructor(
  private val service: LoginService,
) : LoginRemoteDataSource {
  public override suspend fun login(loginRequest: LoginRequest): LoginResponse? {
    try {
      val response = service.login(loginRequest)
      if (response.isSuccessful) {
        return response.body()
      } else if (response.code() == 404) {
        throw LoginApiResponseNotFound
      } else {
        val errorBody = response.errorBody()?.string() ?: "Unknown error"
        Timber.d(Exception(errorBody))
        throw ExceptionWrapper(
          statusCode = response.code(),
          message = Exception(errorBody).toAlertMessage(),
          cause = Exception(errorBody),
        )
      }
    } catch (exception: HttpException) {
      Timber.d(exception)
      throw ExceptionWrapper(
        statusCode = exception.code(),
        message = exception.response()?.errorBody()?.string() ?: exception.message(),
        cause = exception,
      )
    } catch (exception: UnknownHostException) {
      Timber.d(exception)
      throw ExceptionWrapper(
        message = exception.toAlertMessage(),
        cause = exception,
      )
    } catch (exception: Exception) {
      Timber.d(exception)
      throw ExceptionWrapper(
        message = exception.toAlertMessage(),
        cause = exception,
      )
    }
  }

  public override suspend fun signUp(
    signUpRequest: SignUpRequest,
  ): SignUpResponse? {
    return safeRequest {
      service.signUp(signUpRequest)
    }
  }
}
