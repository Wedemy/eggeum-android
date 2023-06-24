package us.wedemy.eggeum.registercafe.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import us.wedemy.eggeum.registercafe.databinding.ItemCafeImageBinding
import us.wedemy.eggeum.registercafe.item.CafeImageItem

class CafeImageViewHolder(val binding: ItemCafeImageBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bind(cafeImageItem: CafeImageItem) {
    binding.ivCafeImage.load(cafeImageItem.url)
  }
}
