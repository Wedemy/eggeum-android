/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.extensions

import java.net.UnknownHostException
import retrofit2.HttpException

internal fun Exception.toAlertMessage(): String {
  return when (this) {
    is HttpException -> "서버에 문제가 발생했어요. 잠시 후 다시 시도해주세요."
    is UnknownHostException -> "네트워크 연결을 확인해주세요."
    else -> "예기치 못한 오류가 발생했어요. 잠시 후 다시 시도해주세요."
  }
}
