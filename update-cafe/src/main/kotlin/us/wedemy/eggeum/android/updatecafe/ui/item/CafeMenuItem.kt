
/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.updatecafe.ui.item

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import us.wedemy.eggeum.android.domain.model.place.ProductEntity

@Parcelize
data class CafeMenuItem(
  val name: String,
  val price: Int,
) : Parcelable {
  fun toEntity(): ProductEntity = ProductEntity(
    name = name,
    price = price,
  )
}
