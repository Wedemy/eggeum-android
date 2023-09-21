/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter

import android.view.ViewGroup
import us.wedemy.eggeum.android.common.extension.layoutInflater
import us.wedemy.eggeum.android.common.ui.BaseRecyclerViewAdapter
import us.wedemy.eggeum.android.main.databinding.ItemCafeMenuBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.CafeMenuViewHolder
import us.wedemy.eggeum.android.main.ui.item.CafeMenuItem

class CafeMenuAdapter : BaseRecyclerViewAdapter<CafeMenuItem, ItemCafeMenuBinding>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    CafeMenuViewHolder(ItemCafeMenuBinding.inflate(parent.context.layoutInflater, parent, false))
}
