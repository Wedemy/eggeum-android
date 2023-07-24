/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import us.wedemy.eggeum.android.main.databinding.ItemCafeImageDetailBinding

class CafeImageDetailViewHolder(val binding: ItemCafeImageDetailBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(imageUrl: String) {
    binding.ivCafeImageDetail.load(imageUrl)
  }
}
