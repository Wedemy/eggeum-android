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
import us.wedemy.eggeum.android.main.databinding.ItemNewCafeBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NewCafeViewHolder
import us.wedemy.eggeum.android.main.ui.item.NewCafeItem

class NewCafeAdapter(
  private var cafeList: List<NewCafeItem> = emptyList(),
  private val clickListener: (Int) -> Unit,
) : RecyclerView.Adapter<NewCafeViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    NewCafeViewHolder(ItemNewCafeBinding.inflate(parent.context.layoutInflater, parent, false))

  override fun onBindViewHolder(holder: NewCafeViewHolder, position: Int) {
    val cafe = cafeList[position]
    with(holder) {
      bind(cafe)
      binding.root.setOnClickListener {
        clickListener(adapterPosition)
      }
    }
  }

  override fun getItemCount() = cafeList.size
}
