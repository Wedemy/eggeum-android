/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.main.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.common.ui.BaseFragment
import us.wedemy.eggeum.main.databinding.FragmentHomeBinding
import us.wedemy.eggeum.main.ui.adapter.NewCafeAdapter
import us.wedemy.eggeum.main.ui.adapter.NoticeAdapter
import us.wedemy.eggeum.main.ui.item.NewCafeItem
import us.wedemy.eggeum.main.ui.item.NoticeItem
import us.wedemy.eggeum.main.viewmodel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
  override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<HomeViewModel>()

  private val newCafeAdapter by lazy {
    NewCafeAdapter { _ -> run {} }
  }

  private val noticeAdapter by lazy {
    NoticeAdapter { _ -> run {} }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    val newCafes = listOf(
      NewCafeItem("스타벅스 강남역신분당역사점", "서울특별시 강남구 강남대로 396"),
      NewCafeItem("아티제 삼성타운점", "서울특별시 강남구 서초대로74길 11"),
      NewCafeItem("스타벅스 강남R점", "서울특별시 강남구 강남대로 390"),
    )
    val newStudyCafes = listOf(
      NewCafeItem("세컨드 라이브러리", "서울특별시 강남구 영동대로137길 6"),
      NewCafeItem("데일리스터디카페 대치점", "서울특별시 강남구 삼성로58길 13"),
      NewCafeItem("랭스터디카페 대치점", "서울특별시 강남구 도곡로 446"),
    )
    val newStudyRooms = listOf(
      NewCafeItem("맥스터디 24시", "서울특별시 강남구 개포로 508"),
      NewCafeItem("토즈 워크센터 선릉점", "서울특별시 강남구 테헤란로52길 21"),
      NewCafeItem("옐로스톤 스터디룸", "서울특별시 강남구 강남대로94길 21"),
    )

    val notices = listOf(
      NoticeItem("공부하기 좋은 카페 찾는 법", "23.03.01"),
      NoticeItem("카페 평가하는 법", "23.03.01"),
      NoticeItem("일이삼사오육칠팔구십일이삼사오육", "23.03.01"),
      NoticeItem("공부하기 좋은 카페 찾는 법", "23.03.01"),
      NoticeItem("카페 평가하는 법", "23.03.01"),
      NoticeItem("일이삼사오육칠팔구십일이삼사오육", "23.03.01"),
    )

    with(binding) {
      rvHomeNewCafe.apply {
        setHasFixedSize(true)
        adapter = newCafeAdapter
        isNestedScrollingEnabled = false
        addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
      }

      rvHomeNotice.apply {
        setHasFixedSize(true)
        adapter = noticeAdapter
      }
      newCafeAdapter.submitList(newCafes)
      noticeAdapter.submitList(notices)

      binding.tlHomeNewCafe.apply {
        post {
          getTabAt(0)?.select()
        }

        addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
          override fun onTabSelected(tab: TabLayout.Tab) {
            when (tab.position) {
              0 -> newCafeAdapter.submitList(newCafes)
              1 -> newCafeAdapter.submitList(newStudyCafes)
              2 -> newCafeAdapter.submitList(newStudyRooms)
            }
          }

          override fun onTabUnselected(tab: TabLayout.Tab) {}
          override fun onTabReselected(tab: TabLayout.Tab) {}
        })
      }
    }
  }

  private fun initListener() {}

  private fun initObserver() {}
}
