/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.place

public data class Product(
  val name: String,
  val price: Int,
) {
  public companion object {
    public fun fixture(): Product {
      return Product(
        name = "",
        price = 0,
      )
    }
  }
}
