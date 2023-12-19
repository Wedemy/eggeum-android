/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.registercafe.adapter.viewholder

import coil.load
import us.wedemy.eggeum.android.common.ui.BaseViewHolder
import us.wedemy.eggeum.android.registercafe.adapter.listener.CafeImageClickListener
import us.wedemy.eggeum.android.registercafe.databinding.ItemCafeImageBinding
import us.wedemy.eggeum.android.registercafe.model.CafeImageItem

class CafeImageViewHolder(
  binding: ItemCafeImageBinding,
  clickListener: CafeImageClickListener,
) : BaseViewHolder<CafeImageItem, ItemCafeImageBinding>(binding) {

  init {
    binding.ivCafeImageClose.setOnClickListener {
      clickListener.onItemClick(adapterPosition)
    }
  }
  override fun bind(item: CafeImageItem) {
    binding.ivCafeImage.load(item.url)
  }
}
