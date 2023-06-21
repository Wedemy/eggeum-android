package us.wedemy.eggeum.registercafe.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import us.wedemy.eggeum.registercafe.databinding.ItemCafeImageBinding
import us.wedemy.eggeum.registercafe.item.CafeImageItem

class CafeImageViewHolder(
  val binding: ItemCafeImageBinding,
) : RecyclerView.ViewHolder(binding.root) {
  fun bind(cafeImageItem: CafeImageItem) {
    itemView.apply {
      binding.apply {
        Glide.with(itemView.context)
          .load(cafeImageItem.url)
          .into(ivCafeImage)
      }
    }
  }
}
