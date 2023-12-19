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
import us.wedemy.eggeum.android.updatecafe.ui.DeleteOnClickListener
import us.wedemy.eggeum.android.updatecafe.ui.EditOnClickListener
import us.wedemy.eggeum.android.updatecafe.ui.item.CafeMenuItem

class CafeMenuViewHolder(
  binding: ItemUpdateCafeMenuBinding,
  editOnClickListener: EditOnClickListener,
  deleteOnClickListener: DeleteOnClickListener,
) : BaseViewHolder<CafeMenuItem, ItemUpdateCafeMenuBinding>(binding) {

  private val clickOnEditBtn by lazy {
    editOnClickListener
  }

  private val clickOnDeleteBtn by lazy {
    deleteOnClickListener
  }

  override fun bind(item: CafeMenuItem) {
    binding.apply {
      tvCafeMenuName.text = item.name
      tvCafeMenuPrice.text = formatPrice(item.price)

      ivCafeMenuOption.setOnClickListener {
        val popupMenu = PopupMenu(binding.root.context, it)
        popupMenu.menuInflater.inflate(R.menu.edit_menu, popupMenu.menu)

        popupMenu.setForceShowIcon(true)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
          when (it.itemId) {
            R.id.menu_edit -> {
              clickOnEditBtn.editBtnClickListener(cafeMenu = item)
              true
            }
            R.id.menu_delete -> {
              clickOnDeleteBtn.deleteBtnClickListener(cafeMenu = item)
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
