/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.addDivider
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.common.util.HorizontalSpacingItemDecoration
import us.wedemy.eggeum.android.design.R
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.main.databinding.FragmentHomeBinding
import us.wedemy.eggeum.android.main.ui.adapter.SearchCafeAdapter
import us.wedemy.eggeum.android.main.ui.adapter.NewCafeAdapter
import us.wedemy.eggeum.android.main.ui.adapter.NoticeCardAdapter
import us.wedemy.eggeum.android.main.ui.adapter.listener.SearchCafeClickListener
import us.wedemy.eggeum.android.main.ui.adapter.listener.NoticeCardClickListener
import us.wedemy.eggeum.android.main.viewmodel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
  override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<HomeViewModel>()

  private val searchCafeAdapter by lazy { SearchCafeAdapter() }

  private val newCafeAdapter by lazy {
    NewCafeAdapter(
      object : SearchCafeClickListener {
        override fun onItemClick(item: PlaceEntity) {
          Toast.makeText(requireContext(), "${item.name}을 선택했습니다.", Toast.LENGTH_SHORT).show()
        }
      },
    )
  }

  private val noticeCardAdapter by lazy {
    NoticeCardAdapter(
      object : NoticeCardClickListener {
        override fun onItemClick(position: Int) {
          // TODO 화면 전환 클릭 이벤트 리스터 구현
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
          newCafeAdapter.replaceAll(viewModel.cafesList.value[tab.position])
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
        viewModel.cafeList.collectLatest { cafes ->
          searchCafeAdapter.submitData(cafes)
        }
      }

      launch {
        searchCafeAdapter.loadStateFlow
          .distinctUntilChangedBy { it.refresh }
          .collect { loadStates ->
            if (loadStates.source.refresh is LoadState.NotLoading) {
              viewModel.getNewCafeList(searchCafeAdapter.snapshot())
            }
          }
      }

      launch {
        viewModel.cafesList.collect { cafesList ->
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
