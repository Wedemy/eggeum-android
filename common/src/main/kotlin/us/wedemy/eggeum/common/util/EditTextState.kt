/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.common.util

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

sealed class EditTextState {
  @Parcelize
  object Success : EditTextState(), Parcelable

  @Parcelize
  data class Error(@StringRes val stringRes: Int? = null) : EditTextState(), Parcelable

  @Parcelize
  object Idle : EditTextState(), Parcelable
}
