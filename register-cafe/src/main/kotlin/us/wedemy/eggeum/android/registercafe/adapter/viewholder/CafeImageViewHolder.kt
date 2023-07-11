/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.registercafe.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import us.wedemy.eggeum.android.registercafe.databinding.ItemCafeImageBinding
import us.wedemy.eggeum.android.registercafe.item.CafeImageItem

class CafeImageViewHolder(val binding: ItemCafeImageBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(cafeImageItem: CafeImageItem) {
    binding.ivCafeImage.load(cafeImageItem.url)
  }
}
