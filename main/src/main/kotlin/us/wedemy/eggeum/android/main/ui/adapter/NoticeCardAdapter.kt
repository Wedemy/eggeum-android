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
import us.wedemy.eggeum.android.main.databinding.ItemNoticeCardBinding
import us.wedemy.eggeum.android.main.ui.adapter.viewholder.NoticeCardViewHolder
import us.wedemy.eggeum.android.main.ui.home.NoticeCardClickListener
import us.wedemy.eggeum.android.main.ui.item.NoticeCardItem

class NoticeCardAdapter(
  private val clickListener: NoticeCardClickListener,
) : BaseRecyclerViewAdapter<NoticeCardItem, ItemNoticeCardBinding>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    NoticeCardViewHolder(ItemNoticeCardBinding.inflate(parent.context.layoutInflater, parent, false), clickListener)
}
