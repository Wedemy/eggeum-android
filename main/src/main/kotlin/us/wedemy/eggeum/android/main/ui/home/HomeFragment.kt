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
import timber.log.Timber
import us.wedemy.eggeum.android.common.extension.addDivider
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.common.util.HorizontalSpacingItemDecoration
import us.wedemy.eggeum.android.design.R
import us.wedemy.eggeum.android.main.databinding.FragmentHomeBinding
import us.wedemy.eggeum.android.main.ui.adapter.NewCafeAdapter
import us.wedemy.eggeum.android.main.ui.adapter.CafePagingAdapter
import us.wedemy.eggeum.android.main.ui.adapter.NoticeCardAdapter
import us.wedemy.eggeum.android.main.ui.adapter.listener.NewCafeClickListener
import us.wedemy.eggeum.android.main.ui.adapter.listener.NoticeCardClickListener
import us.wedemy.eggeum.android.main.viewmodel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
  override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<HomeViewModel>()

  private val cafePagingAdapter by lazy {
    CafePagingAdapter(
      object : NewCafeClickListener {
        override fun onItemClick(position: Int) {
          // TODO
        }
      },
    )
  }

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

//  private val newCafes = listOf(
//    NewCafeModel("스타벅스 강남역신분당역사점", "서울특별시 강남구 강남대로 396"),
//    NewCafeModel("아티제 삼성타운점", "서울특별시 강남구 서초대로74길 11"),
//    NewCafeModel("스타벅스 강남R점", "서울특별시 강남구 강남대로 390"),
//  )
//  private val newStudyCafes = listOf(
//    NewCafeModel("세컨드 라이브러리", "서울특별시 강남구 영동대로137길 6"),
//    NewCafeModel("데일리스터디카페 대치점", "서울특별시 강남구 삼성로58길 13"),
//    NewCafeModel("랭스터디카페 대치점", "서울특별시 강남구 도곡로 446"),
//  )
//  private val newStudyRooms = listOf(
//    NewCafeModel("맥스터디 24시", "서울특별시 강남구 개포로 508"),
//    NewCafeModel("토즈 워크센터 선릉점", "서울특별시 강남구 테헤란로52길 21"),
//    NewCafeModel("옐로스톤 스터디룸", "서울특별시 강남구 강남대로94길 21"),
//  )

//  private val notices = listOf(
//    NoticeCardModel(1, "공부하기 좋은 카페 찾는 법", "23.03.01"),
//    NoticeCardModel(2, "카페 평가하는 법", "23.03.01"),
//    NoticeCardModel(3, "일이삼사오육칠팔구십일이삼사오육", "23.03.01"),
//    NoticeCardModel(4, "공부하기 좋은 카페 찾는 법", "23.03.01"),
//    NoticeCardModel(5, "카페 평가하는 법", "23.03.01"),
//    NoticeCardModel(6, "일이삼사오육칠팔구십일이삼사오육", "23.03.01"),
//  )

//  val cafeLists = listOf(
//    newCafes,
//    newStudyCafes,
//    newStudyRooms,
//  )

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
      // noticeCardAdapter.replaceAll(notices)

      tlHomeNewCafe.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
          // newCafeAdapter.replaceAll(cafeLists[tab.position])
          newCafeAdapter.replaceAll(viewModel.cafesList.value[tab.position])
        }

        override fun onTabUnselected(tab: TabLayout.Tab) = Unit
        override fun onTabReselected(tab: TabLayout.Tab) = Unit
      })
      tlHomeNewCafe.getTabAt(0)?.select()
      // newCafeAdapter.replaceAll(cafeLists[0])
      newCafeAdapter.replaceAll(viewModel.cafesList.value[0])
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
          Timber.tag("cafesList").d("$cafesList")
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
