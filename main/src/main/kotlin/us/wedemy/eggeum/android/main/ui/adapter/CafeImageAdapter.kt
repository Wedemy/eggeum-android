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
import us.wedemy.eggeum.android.main.databinding.ItemCafeImageBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.CafeImageViewHolder

class CafeImageAdapter(private var imageUrlList: List<String> = emptyList()) :
  RecyclerView.Adapter<CafeImageViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    CafeImageViewHolder(
      ItemCafeImageBinding.inflate(parent.context.layoutInflater, parent, false),
    )

  override fun onBindViewHolder(holder: CafeImageViewHolder, position: Int) {
    val imageUrl = imageUrlList[position]
    holder.bind(imageUrl)
  }

  override fun getItemCount() = imageUrlList.size

  fun submitList(list: List<String>) {
    imageUrlList = list
    notifyDataSetChanged()
  }
}
