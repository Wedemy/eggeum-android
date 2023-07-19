/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import us.wedemy.eggeum.android.main.databinding.ItemCafeImageBinding

class CafeImageAdapter(private var imageUrlList: List<String> = emptyList()) :
  RecyclerView.Adapter<CafeImageAdapter.ViewHolder>() {
  inner class ViewHolder(val binding: ItemCafeImageBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int) {
      binding.ivCafeImage.load(imageUrlList[position])
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = ItemCafeImageBinding.inflate(layoutInflater, parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(position)
  }

  override fun getItemCount() = imageUrlList.size

  fun submitList(list: List<String>) {
    imageUrlList = list
    notifyDataSetChanged()
  }
}
