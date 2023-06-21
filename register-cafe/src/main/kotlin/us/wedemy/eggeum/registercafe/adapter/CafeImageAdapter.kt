package us.wedemy.eggeum.registercafe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import us.wedemy.eggeum.registercafe.adapter.viewholder.CafeImageViewHolder
import us.wedemy.eggeum.registercafe.databinding.ItemCafeImageBinding
import us.wedemy.eggeum.registercafe.item.CafeImageItem

class CafeImageAdapter(private val clickListener: (Int) -> Unit) :
  ListAdapter<CafeImageItem, CafeImageViewHolder>(CafeImageItemDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeImageViewHolder {
    return CafeImageViewHolder(
      ItemCafeImageBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    )
  }

  override fun onBindViewHolder(holder: CafeImageViewHolder, position: Int) {
    val cafeImageItem = getItem(position)

    with(holder) {
      cafeImageItem?.let { cafeImage ->
        bind(cafeImage)
        binding.ivCafeImageClose.setOnClickListener {
          clickListener(adapterPosition)
        }
      } ?: run {
        binding.ivCafeImageClose.setOnClickListener(null)
      }
    }
  }

  private companion object {
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
