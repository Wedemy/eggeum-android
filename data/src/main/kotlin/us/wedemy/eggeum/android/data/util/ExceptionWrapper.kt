/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.util

internal data class ExceptionWrapper(
  val statusCode: Int? = null,
  override val message: String? = null,
  override val cause: Throwable? = null,
) : Exception(message, cause)
