package us.wedemy.eggeum.register_cafe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import us.wedemy.eggeum.register_cafe.adapter.viewholder.CafeImageViewHolder
import us.wedemy.eggeum.register_cafe.databinding.ItemCafeImageBinding
import us.wedemy.eggeum.register_cafe.item.CafeImageItem

class CafeImageAdapter : ListAdapter<CafeImageItem, CafeImageViewHolder>(
  CafeImageItemDiffCallback
) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeImageViewHolder {
    return CafeImageViewHolder(
      ItemCafeImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun onBindViewHolder(holder: CafeImageViewHolder, position: Int) {
    val cafeImageItem = getItem(position)
    cafeImageItem?.let { cafeImage ->
      holder.bind(cafeImage)
      holder.itemView.setOnClickListener {}
    }
  }

  private var onItemClickListener: (() -> Unit)? = null
  fun setOnItemClickListener(listener: () -> Unit) {
    onItemClickListener = listener
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