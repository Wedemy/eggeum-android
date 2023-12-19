/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.place

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// @JsonClass(generateAdapter = true)
// public data class Menu(
//   @Json(name = "products")
//   val products: List<Product>,
// )

@Serializable
public data class Menu(
  @SerialName("products")
  val products: List<Product>?,
)
