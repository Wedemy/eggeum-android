/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import java.text.NumberFormat
import java.util.Locale
import us.wedemy.eggeum.android.common.base.BaseViewHolder
import us.wedemy.eggeum.android.main.databinding.ItemCafeMenuBinding
import us.wedemy.eggeum.android.common.model.ProductModel

class CafeMenuViewHolder(binding: ItemCafeMenuBinding) : BaseViewHolder<ProductModel, ItemCafeMenuBinding>(binding) {

  override fun bind(item: ProductModel) {
    binding.apply {
      tvCafeMenuName.text = item.name
      tvCafeMenuPrice.text = formatPrice(item.price)
    }
  }

  private fun formatPrice(price: Int): String {
    val format = NumberFormat.getNumberInstance(Locale.KOREA)
    return "₩" + format.format(price)
  }
}
