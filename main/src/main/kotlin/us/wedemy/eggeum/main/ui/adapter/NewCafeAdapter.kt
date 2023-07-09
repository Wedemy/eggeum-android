/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.main.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import us.wedemy.eggeum.common.extension.layoutInflater
import us.wedemy.eggeum.main.databinding.ItemNewCafeBinding
import us.wedemy.eggeum.main.ui.adapter.viewholder.NewCafeViewHolder
import us.wedemy.eggeum.main.ui.item.NewCafeItem

class NewCafeAdapter(private val clickListener: (Int) -> Unit) :
  ListAdapter<NewCafeItem, NewCafeViewHolder>(NewCafeItemDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    NewCafeViewHolder(ItemNewCafeBinding.inflate(parent.context.layoutInflater, parent, false))

  override fun onBindViewHolder(holder: NewCafeViewHolder, position: Int) {
    val newCafeItem = getItem(position)

    with(holder) {
      newCafeItem?.let { newCafe ->
        bind(newCafe)
        binding.root.setOnClickListener {
          clickListener(adapterPosition)
        }
      } ?: binding.root.setOnClickListener(null)
    }
  }

  private companion object {
    private val NewCafeItemDiffCallback = object : DiffUtil.ItemCallback<NewCafeItem>() {
      override fun areItemsTheSame(oldItem: NewCafeItem, newItem: NewCafeItem) =
        oldItem.cafeAddress == newItem.cafeAddress

      override fun areContentsTheSame(oldItem: NewCafeItem, newItem: NewCafeItem) = oldItem == newItem
    }
  }
}