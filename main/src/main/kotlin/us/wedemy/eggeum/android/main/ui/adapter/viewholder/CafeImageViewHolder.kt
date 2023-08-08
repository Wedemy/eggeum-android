/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import us.wedemy.eggeum.android.main.databinding.ItemCafeImageBinding

class CafeImageViewHolder(val binding: ItemCafeImageBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(cafeImage: String) {
    binding.ivCafeImage.load(cafeImage)
  }
}
