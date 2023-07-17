/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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
    showFragment(CafeInfoFragment())

    binding.tlCafeDetail.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
      override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.position) {
          0 -> showFragment(CafeInfoFragment())
          1 -> showFragment(CafeImageFragment())
          2 -> showFragment(CafeMenuFragment())
        }
      }

      override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
      override fun onTabReselected(tab: TabLayout.Tab?) = Unit
    })
  }

  private fun showFragment(fragment: Fragment) {
    val existingFragment = childFragmentManager.findFragmentByTag(fragment::class.java.simpleName)
    childFragmentManager.beginTransaction().apply {
      childFragmentManager.fragments.forEach { hide(it) }
      if (existingFragment == null) {
        add(R.id.child_fragment_container, fragment, fragment::class.java.simpleName)
      } else {
        show(existingFragment)
      }
      commit()
    }
  }
}
