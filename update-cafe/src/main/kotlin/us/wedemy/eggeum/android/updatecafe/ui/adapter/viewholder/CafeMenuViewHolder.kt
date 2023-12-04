/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.updatecafe.ui.adapter.viewholder

import android.widget.PopupMenu
import java.text.NumberFormat
import java.util.Locale
import us.wedemy.eggeum.android.common.ui.BaseViewHolder
import us.wedemy.eggeum.android.updatecafe.R
import us.wedemy.eggeum.android.updatecafe.databinding.ItemUpdateCafeMenuBinding
import us.wedemy.eggeum.android.updatecafe.ui.item.CafeMenuItem

class CafeMenuViewHolder(binding: ItemUpdateCafeMenuBinding) : BaseViewHolder<CafeMenuItem, ItemUpdateCafeMenuBinding>(binding) {

  override fun bind(item: CafeMenuItem) {
    binding.apply {
      tvCafeMenuName.text = item.name
      tvCafeMenuPrice.text = formatPrice(item.price)

      ivBtn.setOnClickListener {
        val popupMenu = PopupMenu(binding.root.context, it)
        popupMenu.menuInflater.inflate(R.menu.edit_menu, popupMenu.menu)

        popupMenu.setForceShowIcon(true)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
          when (it.itemId) {
            R.id.menu_edit -> {
              // 수정 페이지로 이동하려는데, navigator가 fragment 단에 있어서 어떻게 처리해야 할지 고민입니다.
              true
            }
            R.id.menu_edit -> {
              // 삭제를 선택하면, 다이얼 로그를 띄워서 확인 누르면, api를 쏘게 하고 싶은데 이것도 마찬가지로 viewmodel 단 메서드를 어떻게 호출시켜야 하는지 고민입니다.
              true
            }
            else -> false
          }
        }
      }
    }
  }

  private fun formatPrice(price: Int): String {
    val format = NumberFormat.getNumberInstance(Locale.KOREA)
    return "₩" + format.format(price)
  }
}
