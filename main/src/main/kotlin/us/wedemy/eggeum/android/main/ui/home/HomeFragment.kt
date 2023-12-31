/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.addDivider
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.common.util.HorizontalSpacingItemDecoration
import us.wedemy.eggeum.android.design.R
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.main.databinding.FragmentHomeBinding
import us.wedemy.eggeum.android.main.mapper.toUiModel
import us.wedemy.eggeum.android.main.ui.adapter.NewCafeAdapter
import us.wedemy.eggeum.android.main.ui.adapter.NoticeCardAdapter
import us.wedemy.eggeum.android.main.ui.adapter.SearchCafeAdapter
import us.wedemy.eggeum.android.main.ui.adapter.listener.NoticeCardClickListener
import us.wedemy.eggeum.android.main.ui.adapter.listener.SearchCafeClickListener
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel
import us.wedemy.eggeum.android.main.viewmodel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
  override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

  private val cafeDetailViewModel by activityViewModels<CafeDetailViewModel>()
  private val homeViewModel by viewModels<HomeViewModel>()

  private val searchCafeAdapter by lazy { SearchCafeAdapter() }

  private val newCafeAdapter by lazy {
    NewCafeAdapter(
      object : SearchCafeClickListener {
        override fun onItemClick(item: PlaceEntity) {
          cafeDetailViewModel.setCafeDetailInfo(item.toUiModel())
          val action = HomeFragmentDirections.actionFragmentHomeToFragmentMap()
          findNavController().safeNavigate(action)
        }
      },
    )
  }

  private val noticeCardAdapter by lazy {
    NoticeCardAdapter(
      object : NoticeCardClickListener {
        override fun onItemClick(position: Int) {
          val action = HomeFragmentDirections.actionFragmentHomeToFragmentNotice()
          findNavController().safeNavigate(action)
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
          newCafeAdapter.replaceAll(homeViewModel.cafesList.value[tab.position])
        }

        override fun onTabUnselected(tab: TabLayout.Tab) = Unit
        override fun onTabReselected(tab: TabLayout.Tab) = Unit
      })
      tlHomeNewCafe.getTabAt(0)?.select()
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        homeViewModel.cafeList.collectLatest { cafes ->
          searchCafeAdapter.submitData(cafes)
        }
      }

      launch {
        searchCafeAdapter.loadStateFlow
          .distinctUntilChangedBy { it.refresh }
          .collect { loadStates ->
            if (loadStates.source.refresh is LoadState.NotLoading) {
              homeViewModel.getNewCafeList(searchCafeAdapter.snapshot())
            }
          }
      }

      launch {
        homeViewModel.cafesList.collect { cafesList ->
          newCafeAdapter.replaceAll(cafesList[0])
        }
      }

      launch {
        homeViewModel.noticeList.collectLatest { notices ->
          noticeCardAdapter.submitData(notices)
        }
      }
    }
  }
}
