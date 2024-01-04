/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import us.wedemy.eggeum.android.common.ui.BaseViewHolder
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.main.databinding.ItemSearchCafeLocationBinding

class SearchCafeViewHolder(binding: ItemSearchCafeLocationBinding) :
  BaseViewHolder<PlaceEntity, ItemSearchCafeLocationBinding>(binding) {

  override fun bind(item: PlaceEntity) {
    binding.tvCafeSearchLocation.text = item.name
    binding.ivCafeSearchLocation.setOnClickListener {

    }
  }
}
