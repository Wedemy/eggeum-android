/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:OptIn(ExperimentalContracts::class)

package us.wedemy.eggeum.android.domain.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.cancellation.CancellationException

internal inline fun <T> runSuspendCatching(block: () -> T): Result<T> {
  contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
  return runCatching(block).also { result ->
    val maybeException = result.exceptionOrNull()
    if (maybeException is CancellationException) throw maybeException
  }
}
