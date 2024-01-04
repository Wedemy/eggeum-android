/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.updatecafe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuModel(
  val products: List<ProductModel> = emptyList(),
) : Parcelable
