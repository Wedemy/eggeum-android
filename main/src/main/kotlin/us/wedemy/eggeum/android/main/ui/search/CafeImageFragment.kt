/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentCafeImageBinding
import us.wedemy.eggeum.android.main.ui.adapter.CafeImageAdapter

interface CafeImageClickListener {
  fun onItemClick(position: Int)
}

@AndroidEntryPoint
class CafeImageFragment : BaseFragment<FragmentCafeImageBinding>() {

  private val cafeImageAdapter by lazy {
    CafeImageAdapter(
      object : CafeImageClickListener {
        override fun onItemClick(position: Int) {
          TODO("Not yet implemented")
        }
      }
    )
  }

  override fun getViewBinding() = FragmentCafeImageBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.rvCafeImage.apply {
      setHasFixedSize(true)
      adapter = cafeImageAdapter
    }
  }
}
