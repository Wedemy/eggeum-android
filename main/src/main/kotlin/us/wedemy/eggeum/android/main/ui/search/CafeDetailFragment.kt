/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.ui.BaseBottomSheetFragment
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.FragmentCafeDetailBinding
import us.wedemy.eggeum.android.main.ui.MainActivity
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel

@AndroidEntryPoint
class CafeDetailFragment : BaseBottomSheetFragment<FragmentCafeDetailBinding>() {
  override fun getViewBinding() = FragmentCafeDetailBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<CafeDetailViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    showFragment(TAG_CAFE_INFO_FRAGMENT)
    binding.apply {
      val cafeDetailInfo = viewModel.cafeDetailInfo.value
      tvCafeDetailName.text = cafeDetailInfo.name
      tvCafeDetailAddress.text = cafeDetailInfo.address1
    }
  }

  private fun initListener() {
    binding.tlCafeDetail.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
      override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
          0 -> showFragment(TAG_CAFE_INFO_FRAGMENT)
          1 -> showFragment(TAG_CAFE_IMAGE_FRAGMENT)
          2 -> showFragment(TAG_CAFE_MENU_FRAGMENT)
        }
      }

      override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
      override fun onTabReselected(tab: TabLayout.Tab?) = Unit
    })

    binding.ivCafeDetailOption.setOnClickListener {
      val popupMenu = PopupMenu(binding.root.context, it)
      popupMenu.menuInflater.inflate(R.menu.cafe_detail_menu, popupMenu.menu)

      popupMenu.setForceShowIcon(true)
      popupMenu.show()

      popupMenu.setOnMenuItemClickListener {
        if (it.itemId == R.id.proposal_info_edit) {
          viewModel.navigateToUpdateCafe()
          true
        } else false
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.navigateToUpdateCafeEvent.collect {
          (activity as MainActivity).navigateToUpdateCafe(viewModel.cafeDetailInfo.value)
        }
      }
    }
  }

  @SuppressLint("CommitTransaction")
  private fun showFragment(fragmentTag: String) {
    val existingFragment = childFragmentManager.findFragmentByTag(fragmentTag)
    childFragmentManager.beginTransaction().apply {
      childFragmentManager.fragments.forEach { hide(it) }
      if (existingFragment == null) {
        val newFragment = when (fragmentTag) {
          TAG_CAFE_INFO_FRAGMENT -> CafeInfoFragment()
          TAG_CAFE_IMAGE_FRAGMENT -> CafeImageFragment()
          TAG_CAFE_MENU_FRAGMENT -> CafeMenuFragment()
          else -> error("Unknown fragment tag: $fragmentTag")
        }
        add(R.id.child_fragment_container, newFragment, fragmentTag)
      } else {
        show(existingFragment)
      }
      commit()
    }
  }

  private companion object {
    private const val TAG_CAFE_INFO_FRAGMENT = "CafeInfoFragment"
    private const val TAG_CAFE_IMAGE_FRAGMENT = "CafeImageFragment"
    private const val TAG_CAFE_MENU_FRAGMENT = "CafeMenuFragment"
  }
}
