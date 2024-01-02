/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.util

import java.io.IOException
import java.net.UnknownHostException
import retrofit2.HttpException
import retrofit2.Response
import us.wedemy.eggeum.android.data.extensions.toAlertMessage

@Suppress("TooGenericExceptionCaught")
internal suspend fun <T> safeRequest(request: suspend () -> Response<T>): T? {
  try {
    val response = request()
    if (response.isSuccessful) {
      return response.body()
    } else {
      val errorBody = response.errorBody()?.string() ?: "Unknown error"
      throw ExceptionWrapper(
        statusCode = response.code(),
        message = Exception(errorBody).toAlertMessage(),
        cause = Exception(errorBody),
      )
    }
  } catch (exception: HttpException) {
    throw ExceptionWrapper(
      statusCode = exception.code(),
      message = exception.response()?.errorBody()?.string() ?: exception.message(),
      cause = exception,
    )
  } catch (exception: UnknownHostException) {
    throw ExceptionWrapper(
      message = exception.toAlertMessage(),
      cause = exception,
    )
  } catch (exception: IOException) {
    throw ExceptionWrapper(
      message = exception.toAlertMessage(),
      cause = exception,
    )
  } catch (exception: Exception) {
    throw ExceptionWrapper(
      message = exception.toAlertMessage(),
      cause = exception,
    )
  }
}
