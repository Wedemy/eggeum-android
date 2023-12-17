/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.main.databinding.ItemNewCafeBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.CafeViewHolder

class CafePagingAdapter : PagingDataAdapter<PlaceEntity, CafeViewHolder>(PlaceEntityDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    CafeViewHolder(
      ItemNewCafeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
      null,
    )

  override fun onBindViewHolder(holder: CafeViewHolder, position: Int) {
    val newCafeItem = getItem(position)
    newCafeItem?.let { cafe ->
      holder.bind(cafe)
    }
  }

  companion object {
    private val PlaceEntityDiffCallback = object : DiffUtil.ItemCallback<PlaceEntity>() {
      override fun areItemsTheSame(
        oldItem: PlaceEntity,
        newItem: PlaceEntity,
      ): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
        oldItem: PlaceEntity,
        newItem: PlaceEntity,
      ): Boolean {
        return oldItem == newItem
      }
    }
  }
}
