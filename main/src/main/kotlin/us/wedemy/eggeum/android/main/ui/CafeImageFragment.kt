/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.databinding.FragmentCafeImageBinding

@AndroidEntryPoint
class CafeImageFragment : BaseFragment<FragmentCafeImageBinding>() {
  override fun getViewBinding() = FragmentCafeImageBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    binding.rvCafeImage.apply {
      setHasFixedSize(true)
      layoutManager = GridLayoutManager(requireContext(), 2)
    }
  }
}
