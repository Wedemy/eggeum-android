/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.item

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
  val nickname: String = "",
  val email: String = "",
  val profileImageUrl: String? = null,
) : Parcelable
