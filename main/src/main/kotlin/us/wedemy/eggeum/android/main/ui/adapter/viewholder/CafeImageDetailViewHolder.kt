/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import coil.load
import us.wedemy.eggeum.android.common.base.BaseViewHolder
import us.wedemy.eggeum.android.main.databinding.ItemCafeImageDetailBinding

class CafeImageDetailViewHolder(binding: ItemCafeImageDetailBinding) :
  BaseViewHolder<String, ItemCafeImageDetailBinding>(binding) {

  override fun bind(item: String) {
    binding.ivCafeImageDetail.load(item)
  }
}
