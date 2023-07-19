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
import us.wedemy.eggeum.android.main.databinding.ItemCafeImageDetailBinding

class CafeImageDetailAdapter(
  private var imageUrlList: MutableList<String> = mutableListOf(),
  private var currentPosition: Int = 0,
) : RecyclerView.Adapter<CafeImageDetailAdapter.ViewHolder>() {

  inner class ViewHolder(val binding: ItemCafeImageDetailBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(position: Int) {
      val adjustedPosition = getAdjustedPosition(position)
      binding.ivCafeImageDetail.load(imageUrlList[adjustedPosition])
    }
  }

  init {
    // Add the first and last items to the end and beginning of the list
    imageUrlList.add(0, imageUrlList.last())
    imageUrlList.add(imageUrlList[1])
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = ItemCafeImageDetailBinding.inflate(layoutInflater, parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(position)
  }

  override fun getItemCount(): Int = imageUrlList.size

  fun submitList(list: List<String>, position: Int) {
    imageUrlList.clear()
    imageUrlList.addAll(list)

    currentPosition = getAdjustedPosition(position)
    notifyDataSetChanged()
  }

  private fun getAdjustedPosition(position: Int): Int {
    // Adjust the position to support infinite scrolling
    if (position == 0) {
      return imageUrlList.size - 2
    } else if (position == imageUrlList.size - 1) {
      return 1
    }
    return position
  }
}
