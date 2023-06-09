/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import us.wedemy.eggeum.android.main.databinding.ItemNewCafeBinding
import us.wedemy.eggeum.android.main.ui.item.NewCafeItem

class NewCafeViewHolder(val binding: ItemNewCafeBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(newCafeItem: NewCafeItem) {
    binding.tvNewCafeName.text = newCafeItem.cafeTitle
    binding.tvNewCafeAddress.text = newCafeItem.cafeAddress
  }
}
