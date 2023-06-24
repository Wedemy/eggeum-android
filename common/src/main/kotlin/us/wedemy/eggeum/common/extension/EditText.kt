/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.common.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun EditText.textChangesAsFlow() =
  callbackFlow {
    val listener = object : TextWatcher {
      override fun afterTextChanged(text: Editable?) = Unit
      override fun beforeTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) = Unit
      override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        trySend(text)
      }
    }
    addTextChangedListener(listener)
    awaitClose { removeTextChangedListener(listener) }
  }
