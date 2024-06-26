/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.fromDpToPx
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.common.util.GridSpacingItemDecoration
import us.wedemy.eggeum.android.main.databinding.FragmentCafeImageBinding
import us.wedemy.eggeum.android.main.ui.adapter.CafeImageAdapter
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel

interface CafeImageClickListener {
  fun onItemClick(position: Int)
}

@AndroidEntryPoint
class CafeImageFragment : BaseFragment<FragmentCafeImageBinding>() {
  override fun getViewBinding() = FragmentCafeImageBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<CafeDetailViewModel>()

  private val cafeImageAdapter by lazy {
    CafeImageAdapter(
      object : CafeImageClickListener {
        override fun onItemClick(position: Int) {
          val cafeImageModel = viewModel.cafeDetailInfo.value.image
          val action = cafeImageModel?.let { cafeImages ->
            MapFragmentDirections.actionFragmentMapToFragmentCafeImageDetail(
              cafeImages = cafeImages,
              currentPosition = position,
            )
          }
          if (action != null) {
            findNavController().safeNavigate(action)
          }
        }
      },
    )
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initObserver()
  }

  private fun initView() {
    binding.rvCafeImage.apply {
      setHasFixedSize(true)
      addItemDecoration(GridSpacingItemDecoration(spanCount = 2, spacing = 16f.fromDpToPx()))
      adapter = cafeImageAdapter
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.cafeDetailInfo.collect { cafeDetailInfo ->
          val cafeImages = cafeDetailInfo.image?.files
          cafeImageAdapter.replaceAll(cafeImages)
          if (cafeImages?.isEmpty() == true) {
            binding.apply {
              tvNoImageFound.visibility = View.VISIBLE
              rvCafeImage.visibility = View.GONE
            }
          } else {
            binding.apply {
              tvNoImageFound.visibility = View.GONE
              rvCafeImage.visibility = View.VISIBLE
            }
          }
        }
      }
    }
  }
}
