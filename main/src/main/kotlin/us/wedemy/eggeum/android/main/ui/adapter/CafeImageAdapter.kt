/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.adapter

import android.view.ViewGroup
import us.wedemy.eggeum.android.common.extension.layoutInflater
import us.wedemy.eggeum.android.common.ui.BaseAdapter
import us.wedemy.eggeum.android.common.ui.BaseViewHolder
import us.wedemy.eggeum.android.main.databinding.ItemCafeImageBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.CafeImageViewHolder
import us.wedemy.eggeum.android.main.ui.search.CafeImageClickListener

class CafeImageAdapter(private val clickListener: CafeImageClickListener) : BaseAdapter<String, ItemCafeImageBinding>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String, ItemCafeImageBinding> =
    CafeImageViewHolder(
      ItemCafeImageBinding.inflate(parent.context.layoutInflater, parent, false), clickListener
    )
}
