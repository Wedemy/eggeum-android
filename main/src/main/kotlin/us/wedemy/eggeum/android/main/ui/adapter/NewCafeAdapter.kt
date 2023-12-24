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
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.main.databinding.ItemNewCafeBinding
import us.wedemy.eggeum.android.main.ui.adapter.listener.SearchCafeClickListener
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NewCafeViewHolder

class NewCafeAdapter(private val clickListener: SearchCafeClickListener) :
  BaseRecyclerViewAdapter<PlaceEntity, ItemNewCafeBinding>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    NewCafeViewHolder(ItemNewCafeBinding.inflate(parent.context.layoutInflater, parent, false), clickListener)
}
