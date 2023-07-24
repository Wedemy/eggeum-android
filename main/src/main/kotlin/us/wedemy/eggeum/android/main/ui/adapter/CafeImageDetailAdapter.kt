/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import us.wedemy.eggeum.android.common.extension.layoutInflater
import us.wedemy.eggeum.android.main.databinding.ItemCafeImageDetailBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.CafeImageDetailViewHolder

class CafeImageDetailAdapter(
  private var imageUrlList: MutableList<String> = mutableListOf(),
  private var currentPosition: Int = 0,
) : RecyclerView.Adapter<CafeImageDetailViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    CafeImageDetailViewHolder(
      ItemCafeImageDetailBinding.inflate(parent.context.layoutInflater, parent, false)
    )

  override fun onBindViewHolder(holder: CafeImageDetailViewHolder, position: Int) {
    val adjustedPosition = getAdjustedPosition(position)
    val imageUrl = imageUrlList[adjustedPosition]
    holder.bind(imageUrl)
  }

  override fun getItemCount(): Int = imageUrlList.size

  fun submitList(list: List<String>, position: Int) {
    imageUrlList.clear()
    imageUrlList.addAll(list)

    currentPosition = getAdjustedPosition(position)
    notifyDataSetChanged()
  }

  private fun getAdjustedPosition(position: Int): Int {
    if (imageUrlList.size <= 1) return 0

    if (position == 0) {
      return imageUrlList.size - 2
    } else if (position == imageUrlList.size - 1) {
      return 1
    }
    return position
  }
}
