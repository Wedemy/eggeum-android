/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.onboard.util

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

fun EditText.textChangesToFlow(): Flow<CharSequence?> {
  return callbackFlow {
    val listener = object : TextWatcher {
      override fun afterTextChanged(p0: Editable?) = Unit
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
      override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        trySend(text)
      }
    }
    addTextChangedListener(listener)
    awaitClose { removeTextChangedListener(listener) }
  }.onStart {
    emit(text)
  }
}

fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
  lifecycleScope.launch {
    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
  }
}

fun NavController.safeNavigate(direction: NavDirections) {
  currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

fun NavController.safeNavigate(
  @IdRes currentDestinationId: Int,
  @IdRes id: Int,
  args: Bundle? = null
) {
  if (currentDestinationId == currentDestination?.id) {
    navigate(id, args)
  }
}