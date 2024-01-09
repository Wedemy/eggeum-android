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
data class CafeDetailModel(
  val address1: String? = "",
  val address2: String? = "",
  val id: Int = -1,
  val image: ImageModel? = ImageModel(),
  val info: InfoModel? = InfoModel(),
  val latitude: Double? = null,
  val longitude: Double? = null,
  val menu: MenuModel? = MenuModel(),
  val name: String = "",
) : Parcelable
