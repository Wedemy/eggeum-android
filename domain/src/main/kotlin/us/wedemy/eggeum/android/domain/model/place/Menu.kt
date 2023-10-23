/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.place

public data class Menu(
  val products: List<Product>,
) {
  public companion object {
    public fun fixture(): Menu {
      return Menu(
        products = listOf(Product.fixture()),
      )
    }
  }
}
