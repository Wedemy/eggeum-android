/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CafeDetailModel (
  val address1: String?,
  val address2: String?,
  val id: Int,
  val image: ImageModel,
  val info: InfoModel,
  val menu: MenuModel,
  val name: String,
): Parcelable