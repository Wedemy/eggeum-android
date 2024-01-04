/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("unused", "UnusedPrivateProperty")

package us.wedemy.eggeum.android.updatecafe.ui

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.domain.model.place.ProductEntity
import us.wedemy.eggeum.android.updatecafe.ui.adapter.CafeMenuAdapter
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentSelectCafeMenuBinding
import us.wedemy.eggeum.android.updatecafe.ui.item.CafeMenuItem
import us.wedemy.eggeum.android.updatecafe.viewmodel.ProposeCafeInfoViewModel

interface EditOnClickListener {
  fun editBtnClickListener(cafeMenu: CafeMenuItem)
}

interface DeleteOnClickListener {
  fun deleteBtnClickListener(cafeMenu: CafeMenuItem)
}

@AndroidEntryPoint
class SelectCafeMenuFragment : BaseFragment<FragmentSelectCafeMenuBinding>() {
  private val cafeMenuAdapter by lazy {
    CafeMenuAdapter(
      object : EditOnClickListener {
        override fun editBtnClickListener(cafeMenu: CafeMenuItem) {
          // TODO: savedInstanceState 방법 알아보기
          val action = SelectCafeMenuFragmentDirections.actionFragmentSelectCafeMenuToFragmentInputCafeMenu(
            CafeMenuItem(
              name = cafeMenu.name,
              price = cafeMenu.price,
            ),
          )
          findNavController().safeNavigate(action)
        }
      },
      object : DeleteOnClickListener {
        override fun deleteBtnClickListener(cafeMenu: CafeMenuItem) {
          showEditOrDeleteDialog(item = cafeMenu)
        }
      },
    )
  }

  override fun getViewBinding() = FragmentSelectCafeMenuBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<ProposeCafeInfoViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initView()
    initListener()
    initObserver()
  }

  private fun showEditOrDeleteDialog(item: CafeMenuItem) {
    val dialog = AlertDialog.Builder(requireContext())
      .setMessage("삭제 하시겠어요?")
      .setPositiveButton("삭제", object : OnClickListener {
        override fun onClick(p0: DialogInterface?, p1: Int) {
          val newCafeMenuList = mutableListOf<CafeMenuItem>()
          val cafeMenuList = viewModel.cafeMenuList.value
          cafeMenuList.forEach {
            if (!(it.name == item.name && it.price == item.price)) {
              newCafeMenuList.add(it)
            }
          }
          viewModel.placeBody.menu?.products = newCafeMenuList.map { it.toEntity() }
          val newPlaceBody = (viewModel.placeBody.menu?.products as MutableList<ProductEntity>)
          val newCafeMenuItemList = viewModel.initializeCafeMenuItem(products = newPlaceBody)
          viewModel.updateCafeMenuList(cafeMenuItemList = newCafeMenuItemList)
        }
      })
      .setNegativeButton("취소", null) // 취소 시에는 동작 없음.
      .show()
    // TODO: 피그마 디자인처럼 다이얼로그 디자인 변경하기
    dialog.apply {
      getButton(DialogInterface.BUTTON_POSITIVE)
        .setTextColor(ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.teal_500))
      getButton(DialogInterface.BUTTON_NEGATIVE)
        .setTextColor(ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.gray_400))
    }
  }

  private fun initView() {
    binding.rvCafeMenuList.apply {
      setHasFixedSize(true)
      adapter = cafeMenuAdapter
    }
  }

  private fun initListener() {
    with(binding) {
      tbSelectInputCafeMenu.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }
      btnInputCafeMenu.setOnClickListener {
        viewModel.updatePlaceBodyUseCase()
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.cafeMenuList.collect { cafeMenuAdapter.replaceAll(it) }
      }
      launch {
        viewModel.navigateToUpsertEvent.collect {
          val action = SelectCafeMenuFragmentDirections.actionFragmentSelectCafeMenuToFragmentUpdateMenuComplete()
          findNavController().safeNavigate(action)
        }
        // TODO: 에뮬레이터와 달리 실기기로 테스트 시, 해당 state value를 초기화 안해도 마지막 완료 화면으로 넘어가지 않음. (확인 필요)
        // viewModel.initializeUpdatePlaceBodySuccess()
      }
      launch {
        viewModel.getInitCall.collect {
          viewModel.editCafeMenuItem()
        }
      }
    }
  }
}
