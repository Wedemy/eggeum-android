/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import us.wedemy.eggeum.android.common.extension.layoutInflater
import us.wedemy.eggeum.android.main.databinding.ItemCafeMenuBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.CafeMenuViewHolder
import us.wedemy.eggeum.android.main.ui.item.CafeMenuItem

class CafeMenuAdapter : ListAdapter<CafeMenuItem, CafeMenuViewHolder>(CafeMenuItemDiffCallback) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    CafeMenuViewHolder(ItemCafeMenuBinding.inflate(parent.context.layoutInflater, parent, false))

  override fun onBindViewHolder(holder: CafeMenuViewHolder, position: Int) {
    val cafeMenuItem = getItem(position)
    cafeMenuItem?.let { cafeMenu -> holder.bind(cafeMenu) }
  }

  private companion object {
    private val CafeMenuItemDiffCallback = object : DiffUtil.ItemCallback<CafeMenuItem>() {
      override fun areItemsTheSame(oldItem: CafeMenuItem, newItem: CafeMenuItem) = oldItem === newItem
      override fun areContentsTheSame(oldItem: CafeMenuItem, newItem: CafeMenuItem) = oldItem == newItem
    }
  }
}
