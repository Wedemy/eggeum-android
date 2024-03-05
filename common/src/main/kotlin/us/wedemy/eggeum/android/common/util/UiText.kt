/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.util

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
  data class DirectString(val value: String) : UiText()

  class StringResource(
    @StringRes val resId: Int,
    vararg val args: Any,
  ) : UiText()

  fun asString(context: Context) = when (this) {
    is DirectString -> value
    is StringResource -> context.getString(resId, *args)
  }
}
