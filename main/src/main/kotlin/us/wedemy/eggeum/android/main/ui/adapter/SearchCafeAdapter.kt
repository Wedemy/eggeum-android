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
import androidx.recyclerview.widget.RecyclerView
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.main.databinding.ItemRecentSearchCafeResultBinding
import us.wedemy.eggeum.android.main.databinding.ItemSearchCafeResultBinding
import us.wedemy.eggeum.android.main.ui.adapter.listener.SearchCafeClickListener
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.RecentSearchCafeViewHolder
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.SearchCafeViewHolder

class SearchCafeAdapter(private val clickListener: SearchCafeClickListener? = null) :
  PagingDataAdapter<PlaceEntity, RecyclerView.ViewHolder>(PlaceEntityDiffCallback) {

  override fun getItemViewType(position: Int): Int {
    return if (getItem(position)?.isRecentSearch == true) {
      VIEW_TYPE_RECENT_SEARCH_CAFE
    } else {
      VIEW_TYPE_SEARCH_CAFE
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when(viewType) {
      VIEW_TYPE_SEARCH_CAFE -> SearchCafeViewHolder(
        ItemSearchCafeResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
      VIEW_TYPE_RECENT_SEARCH_CAFE -> RecentSearchCafeViewHolder(
        ItemRecentSearchCafeResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
      else -> throw IllegalStateException("Unknown viewType $viewType")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val cafeItem = getItem(position)
    cafeItem?.let { cafe ->
      when (holder) {
        is RecentSearchCafeViewHolder -> holder.bind(cafe)
        is SearchCafeViewHolder -> holder.bind(cafe)
      }
      holder.itemView.setOnClickListener {
        clickListener?.onItemSelected(cafe)
      }
      if (holder is RecentSearchCafeViewHolder) {
        holder.binding.ivRecentSearchCafeDelete.setOnClickListener {
          clickListener?.onItemDeleteClick(cafe)
        }
      }
    }
  }

  companion object {
    private const val VIEW_TYPE_RECENT_SEARCH_CAFE = 0
    private const val VIEW_TYPE_SEARCH_CAFE = 1

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
