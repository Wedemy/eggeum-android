/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.updatecafe.ui.adapter.viewholder

import java.text.NumberFormat
import java.util.Locale
import us.wedemy.eggeum.android.common.ui.BaseViewHolder
import us.wedemy.eggeum.android.updatecafe.databinding.ItemUpdateCafeMenuBinding
import us.wedemy.eggeum.android.updatecafe.ui.item.CafeMenuItem

class CafeMenuViewHolder(binding: ItemUpdateCafeMenuBinding) : BaseViewHolder<CafeMenuItem, ItemUpdateCafeMenuBinding>(binding) {

  override fun bind(item: CafeMenuItem) {
    binding.apply {
      tvCafeMenuName.text = item.name
      tvCafeMenuPrice.text = formatPrice(item.price)

      imgBtn.setOnClickListener {
        // TODO 수정 및 삭제 창
      }
    }
  }

  private fun formatPrice(price: Int): String {
    val format = NumberFormat.getNumberInstance(Locale.KOREA)
    return "₩" + format.format(price)
  }
}
