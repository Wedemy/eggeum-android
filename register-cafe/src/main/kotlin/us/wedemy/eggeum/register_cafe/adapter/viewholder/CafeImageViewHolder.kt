package us.wedemy.eggeum.register_cafe.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import us.wedemy.eggeum.register_cafe.databinding.ItemCafeImageBinding
import us.wedemy.eggeum.register_cafe.item.CafeImageItem

class CafeImageViewHolder(
  private val binding: ItemCafeImageBinding,
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