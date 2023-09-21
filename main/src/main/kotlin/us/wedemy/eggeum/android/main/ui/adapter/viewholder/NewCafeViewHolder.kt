/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import us.wedemy.eggeum.android.common.ui.BaseViewHolder
import us.wedemy.eggeum.android.main.databinding.ItemNewCafeBinding
import us.wedemy.eggeum.android.main.ui.home.NewCafeClickListener
import us.wedemy.eggeum.android.main.ui.item.NewCafeItem

class NewCafeViewHolder(
  binding: ItemNewCafeBinding,
  clickListener: NewCafeClickListener,
) : BaseViewHolder<NewCafeItem, ItemNewCafeBinding>(binding) {

  init {
    binding.root.setOnClickListener {
      clickListener.onItemClick(adapterPosition)
    }
  }

  override fun bind(item: NewCafeItem) {
    binding.apply {
      tvNewCafeName.text = item.name
      tvNewCafeAddress.text = item.address
    }
  }
}
