/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

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
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseBottomSheetFragment
import us.wedemy.eggeum.android.main.R
import us.wedemy.eggeum.android.main.databinding.FragmentCafeDetailBinding

@AndroidEntryPoint
class CafeDetailFragment : BaseBottomSheetFragment<FragmentCafeDetailBinding>() {
  override fun getViewBinding() = FragmentCafeDetailBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    showFragment(TAG_CAFE_INFO_FRAGMENT)

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
