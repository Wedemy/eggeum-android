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
  private var imageUrlList: List<String> = emptyList(),
) : RecyclerView.Adapter<CafeImageDetailViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    CafeImageDetailViewHolder(
      ItemCafeImageDetailBinding.inflate(parent.context.layoutInflater, parent, false),
    )

  override fun onBindViewHolder(holder: CafeImageDetailViewHolder, position: Int) {
    val realPosition = position % imageUrlList.size
    val imageUrl = imageUrlList[realPosition]
    holder.bind(imageUrl)
  }

  override fun getItemCount() = if (imageUrlList.size > 1) Integer.MAX_VALUE else imageUrlList.size

  fun submitList(list: List<String>) {
    imageUrlList = list
  }
}
