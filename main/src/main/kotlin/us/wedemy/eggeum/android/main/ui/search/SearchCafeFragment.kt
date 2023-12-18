/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.common.extension.addDivider
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.design.R
import us.wedemy.eggeum.android.main.databinding.FragmentSearchCafeBinding
import us.wedemy.eggeum.android.main.model.CafeDetailModel
import us.wedemy.eggeum.android.main.model.ImageModel
import us.wedemy.eggeum.android.main.model.InfoModel
import us.wedemy.eggeum.android.main.model.MenuModel
import us.wedemy.eggeum.android.main.model.ProductModel

@AndroidEntryPoint
class SearchCafeFragment : BaseFragment<FragmentSearchCafeBinding>() {
  override fun getViewBinding() = FragmentSearchCafeBinding.inflate(layoutInflater)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.rvSearchCafe.apply {
      setHasFixedSize(true)
      addDivider(R.color.gray_300)
    }

    val action = SearchCafeFragmentDirections.actionFragmentSearchCafeToFragmentCafeDetail(
      CafeDetailModel(
        address1 = "서울특별시 강남구 강남대로 396",
        address2 = "",
        id = 1,
        image = ImageModel(
          listOf()
        ),
        info = InfoModel(
          areaSize = "30",
          meetingRoomCount = 3,
          multiSeatCount = 24,
          singleSeatCount = 1,
          businessHours = listOf("매일 09:00 - 21:00"),
          existsSmokingArea = false,
          existsWifi = true,
          mobileCharging = "카운터에서 가능",
          parking = "가능 / 기본 1시간 / 시간당 3,000원",
          phone = "02-123-4567",
          restRoom = "내부 / 남녀분리 / 장애인 화장실 있음",
          websiteUri = "",
          instagramUri = "",
          blogUri = "",
        ),
        menu = MenuModel(
          listOf(ProductModel("아메리카노", 3000), ProductModel("카페라테", 5000),)
        ),
        name = "스타벅스 강남역신분당역사점"
      )
    )
    findNavController().safeNavigate(action)
  }
}
