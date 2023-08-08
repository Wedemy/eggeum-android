/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale
import us.wedemy.eggeum.android.main.databinding.ItemCafeMenuBinding
import us.wedemy.eggeum.android.main.ui.item.CafeMenuItem

class CafeMenuViewHolder(val binding: ItemCafeMenuBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(cafeMenu: CafeMenuItem) {
    binding.tvCafeMenuName.text = cafeMenu.name
    binding.tvCafeMenuPrice.text = formatPrice(cafeMenu.price)
  }

  private fun formatPrice(price: Int): String {
    val format = NumberFormat.getNumberInstance(Locale.KOREA)
    return "₩" + format.format(price)
  }
}
