/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.FragmentCafeImageDetailBinding
import us.wedemy.eggeum.android.main.ui.adapter.CafeImageDetailAdapter
import us.wedemy.eggeum.android.main.viewmodel.CafeImageDetailViewModel

// TODO 툴바에 currentPosition 표기
@AndroidEntryPoint
class CafeImageDetailFragment : BaseFragment<FragmentCafeImageDetailBinding>() {

  private val viewModel by viewModels<CafeImageDetailViewModel>()

  private val cafeImageDetailAdapter by lazy {
    CafeImageDetailAdapter(viewModel.cafeImages.files)
  }

  override fun getViewBinding() = FragmentCafeImageDetailBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    binding.vpCafeImageDetail.apply {
      adapter = cafeImageDetailAdapter
      setCurrentItem(viewModel.currentPosition.value, false)
    }
  }

  private fun initListener() {
    with(binding) {
      tbCafeImageDetail.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      binding.ivCafeImageDetailOption.setOnClickListener {
        val popupMenu = PopupMenu(binding.root.context, it)
        popupMenu.menuInflater.inflate(R.menu.cafe_image_detail_menu, popupMenu.menu)

        popupMenu.setForceShowIcon(true)
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener {
          if (it.itemId == R.id.cafe_image_report) {
            val action = CafeImageDetailFragmentDirections.actionFragmentCafeImageDetailToReportCafeImageFragment()
            findNavController().safeNavigate(action)
            true
          } else false
        }
      }

      ivCafeImageDetailNext.setOnClickListener {
        val currentItem = vpCafeImageDetail.currentItem
        vpCafeImageDetail.setCurrentItem(currentItem + 1, true)
        viewModel.updateCurrentPosition(currentItem + 1)
      }

      ivCafeImageDetailPrev.setOnClickListener {
        val currentItem = vpCafeImageDetail.currentItem
        if (currentItem > 0) {
          vpCafeImageDetail.setCurrentItem(currentItem - 1, true)
          viewModel.updateCurrentPosition(currentItem - 1)
        } else {
          // 이미지가 존재해야 해당 화면에 접근할 수 있으므로 imageUrlList 의 사이즈는 0 이 될 수 없음
          val lastIndex = (vpCafeImageDetail.adapter?.itemCount ?: 0) / viewModel.cafeImages.files.size - 1
          vpCafeImageDetail.setCurrentItem(lastIndex * viewModel.cafeImages.files.size, true)
          viewModel.updateCurrentPosition(lastIndex * viewModel.cafeImages.files.size)
        }
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.currentPosition.collect { index ->
          val text = "${index + 1} / ${viewModel.cafeImages.files.size}"
          val spannableString = SpannableString(text)

          val slashIndex = text.indexOf("/")

          val whiteSpan = ForegroundColorSpan(Color.WHITE)
          spannableString.setSpan(whiteSpan, 0, slashIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

          val graySpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.gray_500))
          spannableString.setSpan(graySpan, slashIndex, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

          binding.tvCafeImageDetailImageIndex.text = spannableString
        }
      }
    }
  }
}
