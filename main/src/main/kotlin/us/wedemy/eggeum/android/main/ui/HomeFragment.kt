/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.common.extension.addDivider
import us.wedemy.eggeum.android.main.databinding.FragmentHomeBinding
import us.wedemy.eggeum.android.main.ui.adapter.NewCafeAdapter
import us.wedemy.eggeum.android.main.ui.adapter.NoticeAdapter
import us.wedemy.eggeum.android.main.ui.item.NewCafeItem
import us.wedemy.eggeum.android.main.ui.item.NoticeItem

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
  override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

  private val newCafeAdapter by lazy {
    NewCafeAdapter { _ -> run {} }
  }

  private val noticeAdapter by lazy {
    NoticeAdapter { _ -> run {} }
  }

  private val newCafes = listOf(
    NewCafeItem("스타벅스 강남역신분당역사점", "서울특별시 강남구 강남대로 396"),
    NewCafeItem("아티제 삼성타운점", "서울특별시 강남구 서초대로74길 11"),
    NewCafeItem("스타벅스 강남R점", "서울특별시 강남구 강남대로 390"),
  )
  private val newStudyCafes = listOf(
    NewCafeItem("세컨드 라이브러리", "서울특별시 강남구 영동대로137길 6"),
    NewCafeItem("데일리스터디카페 대치점", "서울특별시 강남구 삼성로58길 13"),
    NewCafeItem("랭스터디카페 대치점", "서울특별시 강남구 도곡로 446"),
  )
  private val newStudyRooms = listOf(
    NewCafeItem("맥스터디 24시", "서울특별시 강남구 개포로 508"),
    NewCafeItem("토즈 워크센터 선릉점", "서울특별시 강남구 테헤란로52길 21"),
    NewCafeItem("옐로스톤 스터디룸", "서울특별시 강남구 강남대로94길 21"),
  )

  private val notices = listOf(
    NoticeItem("공부하기 좋은 카페 찾는 법", "23.03.01"),
    NoticeItem("카페 평가하는 법", "23.03.01"),
    NoticeItem("일이삼사오육칠팔구십일이삼사오육", "23.03.01"),
    NoticeItem("공부하기 좋은 카페 찾는 법", "23.03.01"),
    NoticeItem("카페 평가하는 법", "23.03.01"),
    NoticeItem("일이삼사오육칠팔구십일이삼사오육", "23.03.01"),
  )

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
        addDivider(us.wedemy.eggeum.android.design.R.color.gray_300)
      }

      rvHomeNotice.apply {
        setHasFixedSize(true)
        adapter = noticeAdapter
      }
      newCafeAdapter.submitList(newCafes)
      noticeAdapter.submitList(notices)

      binding.tlHomeNewCafe.apply {
        val cafeLists = listOf(newCafes, newStudyCafes, newStudyRooms)
        addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
          override fun onTabSelected(tab: TabLayout.Tab) {
            newCafeAdapter.submitList(cafeLists[tab.position])
          }

          override fun onTabUnselected(tab: TabLayout.Tab) = Unit
          override fun onTabReselected(tab: TabLayout.Tab) = Unit
        })
      }
    }
  }

  private fun initListener() {
    // TODO 화면 전환 클릭 이벤트 리스터 구현
  }

  private fun initObserver() {
    // TODO 새로운 카페 목록 및 공지사항 목록 가져오기
  }
}
