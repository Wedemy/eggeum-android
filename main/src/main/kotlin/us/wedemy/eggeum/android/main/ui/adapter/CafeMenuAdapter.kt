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
import us.wedemy.eggeum.android.main.databinding.ItemCafeMenuBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.CafeMenuViewHolder
import us.wedemy.eggeum.android.main.ui.item.CafeMenuItem

class CafeMenuAdapter(
  private var menuList: List<CafeMenuItem> = emptyList(),
) : RecyclerView.Adapter<CafeMenuViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    CafeMenuViewHolder(ItemCafeMenuBinding.inflate(parent.context.layoutInflater, parent, false))

  override fun onBindViewHolder(holder: CafeMenuViewHolder, position: Int) {
    val menu = menuList[position]
    holder.bind(menu)
  }

  override fun getItemCount() = menuList.size
}
