/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.addDivider
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.common.util.HorizontalSpacingItemDecoration
import us.wedemy.eggeum.android.design.R
import us.wedemy.eggeum.android.main.databinding.FragmentHomeBinding
import us.wedemy.eggeum.android.main.ui.adapter.CafePagingAdapter
import us.wedemy.eggeum.android.main.ui.adapter.NewCafeAdapter
import us.wedemy.eggeum.android.main.ui.adapter.NoticeCardAdapter
import us.wedemy.eggeum.android.main.ui.adapter.listener.NewCafeClickListener
import us.wedemy.eggeum.android.main.ui.adapter.listener.NoticeCardClickListener
import us.wedemy.eggeum.android.main.viewmodel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
  override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<HomeViewModel>()

  private val cafePagingAdapter by lazy { CafePagingAdapter() }

  private val newCafeAdapter by lazy {
    NewCafeAdapter(
      object : NewCafeClickListener {
        override fun onItemClick(position: Int) {
          // TODO
        }
      },
    )
  }

  private val noticeCardAdapter by lazy {
    NoticeCardAdapter(
      object : NoticeCardClickListener {
        override fun onItemClick(position: Int) {
          // TODO
        }
      },
    )
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    with(binding) {
      rvHomeNewCafe.apply {
        setHasFixedSize(true)
        adapter = newCafeAdapter
        addDivider(R.color.gray_300)
      }
      val spacing = resources.getDimensionPixelSize(R.dimen.spacing_16dp)

      rvHomeNotice.apply {
        setHasFixedSize(true)
        adapter = noticeCardAdapter
        addItemDecoration(HorizontalSpacingItemDecoration(spacing))
      }

      tlHomeNewCafe.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
          newCafeAdapter.replaceAll(viewModel.cafesList.value[tab.position])
        }

        override fun onTabUnselected(tab: TabLayout.Tab) = Unit
        override fun onTabReselected(tab: TabLayout.Tab) = Unit
      })
      tlHomeNewCafe.getTabAt(0)?.select()
    }
  }

  private fun initListener() {
    // TODO 화면 전환 클릭 이벤트 리스터 구현
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.cafeList.collectLatest { cafes ->
          cafePagingAdapter.submitData(cafes)
          viewModel.getNewCafeList(cafePagingAdapter.snapshot())
        }
      }

      launch {
        viewModel.cafesList.collectLatest { cafesList ->
          newCafeAdapter.replaceAll(cafesList[0])
        }
      }

      launch {
        viewModel.noticeList.collectLatest { notices ->
          noticeCardAdapter.submitData(notices)
        }
      }
    }
  }
}
