/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewAdapter<T, VB : ViewBinding>() : RecyclerView.Adapter<BaseViewHolder<T, VB>>() {
  private val items = mutableListOf<T>()

  override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
    holder.bind(items[position])
  }

  override fun getItemCount() = items.size

  @Suppress("unused")
  open fun replaceAll(newItems: List<T>?) {
    items.apply{
      clear()
      newItems?.let { addAll(it) }
    }
    notifyDataSetChanged()
  }
}
