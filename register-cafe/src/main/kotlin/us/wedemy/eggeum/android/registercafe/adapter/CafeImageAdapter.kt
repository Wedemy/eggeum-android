/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.registercafe.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import us.wedemy.eggeum.android.common.extension.layoutInflater
import us.wedemy.eggeum.android.registercafe.adapter.listener.CafeImageClickListener
import us.wedemy.eggeum.android.registercafe.adapter.viewholder.CafeImageViewHolder
import us.wedemy.eggeum.android.registercafe.databinding.ItemRegisterCafeImageBinding
import us.wedemy.eggeum.android.registercafe.model.CafeImageModel

class CafeImageAdapter(private val clickListener: CafeImageClickListener) :
  ListAdapter<CafeImageModel, CafeImageViewHolder>(CafeImageItemDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    CafeImageViewHolder(ItemRegisterCafeImageBinding.inflate(parent.context.layoutInflater, parent, false), clickListener)

  override fun onBindViewHolder(holder: CafeImageViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  private companion object {
    private val CafeImageItemDiffCallback = object : DiffUtil.ItemCallback<CafeImageModel>() {
      override fun areItemsTheSame(oldItem: CafeImageModel, newItem: CafeImageModel) = oldItem === newItem
      override fun areContentsTheSame(oldItem: CafeImageModel, newItem: CafeImageModel) = oldItem == newItem
    }
  }
}
