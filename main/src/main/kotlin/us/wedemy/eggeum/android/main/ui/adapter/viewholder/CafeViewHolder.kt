/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import us.wedemy.eggeum.android.common.ui.BaseViewHolder
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.main.databinding.ItemNewCafeBinding
import us.wedemy.eggeum.android.main.ui.adapter.listener.NewCafeClickListener

class CafeViewHolder(
  binding: ItemNewCafeBinding,
  clickListener: NewCafeClickListener? = null,
) : BaseViewHolder<PlaceEntity, ItemNewCafeBinding>(binding) {

  init {
    binding.root.setOnClickListener {
      clickListener?.onItemClick(adapterPosition)
    }
  }

  override fun bind(item: PlaceEntity) {
    binding.apply {
      tvNewCafeName.text = item.name
      tvNewCafeAddress.text = item.address1
    }
  }
}
