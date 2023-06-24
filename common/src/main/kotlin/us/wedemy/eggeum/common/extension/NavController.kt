/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("unused")

package us.wedemy.eggeum.common.extension

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigate(direction: NavDirections) {
  currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

fun NavController.safeNavigate(
  @IdRes currentDestinationId: Int,
  @IdRes id: Int,
  args: Bundle? = null,
) {
  if (currentDestinationId == currentDestination?.id) {
    navigate(id, args)
  }
}
