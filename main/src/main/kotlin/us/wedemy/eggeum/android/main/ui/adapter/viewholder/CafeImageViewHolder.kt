/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import coil.load
import us.wedemy.eggeum.android.common.ui.BaseViewHolder
import us.wedemy.eggeum.android.main.databinding.ItemCafeImageBinding
import us.wedemy.eggeum.android.main.model.FileModel
import us.wedemy.eggeum.android.main.ui.search.CafeImageClickListener

class CafeImageViewHolder(binding: ItemCafeImageBinding, clickListener: CafeImageClickListener) :
  BaseViewHolder<FileModel, ItemCafeImageBinding>(binding) {
  init {
    binding.root.setOnClickListener {
      clickListener.onItemClick(adapterPosition)
    }
  }

  override fun bind(item: FileModel) {
    binding.ivCafeImage.load(item.url)
  }
}
