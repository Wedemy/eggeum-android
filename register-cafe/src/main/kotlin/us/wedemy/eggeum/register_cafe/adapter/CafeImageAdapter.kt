package us.wedemy.eggeum.register_cafe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import us.wedemy.eggeum.register_cafe.adapter.viewholder.CafeImageViewHolder
import us.wedemy.eggeum.register_cafe.databinding.ItemCafeImageBinding
import us.wedemy.eggeum.register_cafe.item.CafeImageItem
import us.wedemy.eggeum.register_cafe.ui.CafeImageItemClickListener

class CafeImageAdapter(private val clickListener: CafeImageItemClickListener) :
  ListAdapter<CafeImageItem, CafeImageViewHolder>(
    CafeImageItemDiffCallback
  ) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeImageViewHolder {
    return CafeImageViewHolder(
      ItemCafeImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun onBindViewHolder(holder: CafeImageViewHolder, position: Int) {
    val cafeImageItem = getItem(position)

    with(holder) {
      cafeImageItem?.let { cafeImage ->
        bind(cafeImage)
        binding.ivCafeImageClose.setOnClickListener {
          clickListener.onDeleteClick(adapterPosition)
        }
      } ?: run {
        binding.ivCafeImageClose.setOnClickListener(null)
      }
    }
  }

  companion object {
    private val CafeImageItemDiffCallback = object : DiffUtil.ItemCallback<CafeImageItem>() {
      override fun areItemsTheSame(
        oldItem: CafeImageItem,
        newItem: CafeImageItem,
      ): Boolean {
        return oldItem.url == newItem.url
      }

      override fun areContentsTheSame(
        oldItem: CafeImageItem,
        newItem: CafeImageItem,
      ): Boolean {
        return oldItem == newItem
      }
    }
  }
}