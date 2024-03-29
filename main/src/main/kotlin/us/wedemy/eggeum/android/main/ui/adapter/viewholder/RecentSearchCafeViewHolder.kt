/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import us.wedemy.eggeum.android.common.base.BaseViewHolder
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.main.databinding.ItemRecentSearchCafeResultBinding

class RecentSearchCafeViewHolder(binding: ItemRecentSearchCafeResultBinding) :
  BaseViewHolder<PlaceEntity, ItemRecentSearchCafeResultBinding>(binding) {

  override fun bind(item: PlaceEntity) {
    binding.tvRecentSearchCafeName.text = item.name
  }
}
