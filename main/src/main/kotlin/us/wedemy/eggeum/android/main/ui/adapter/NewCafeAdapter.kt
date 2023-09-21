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
import us.wedemy.eggeum.android.main.databinding.ItemNewCafeBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NewCafeViewHolder
import us.wedemy.eggeum.android.main.ui.home.NewCafeClickListener
import us.wedemy.eggeum.android.main.ui.item.NewCafeItem

class NewCafeAdapter(private val clickListener: NewCafeClickListener) :
  BaseRecyclerViewAdapter<NewCafeItem, ItemNewCafeBinding>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    NewCafeViewHolder(ItemNewCafeBinding.inflate(parent.context.layoutInflater, parent, false), clickListener)
}
