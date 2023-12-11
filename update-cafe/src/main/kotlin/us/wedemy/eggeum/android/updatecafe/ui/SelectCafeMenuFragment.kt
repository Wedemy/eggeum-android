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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.domain.model.place.ProductEntity
import us.wedemy.eggeum.android.updatecafe.ui.adapter.CafeMenuAdapter
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentSelectCafeMenuBinding
import us.wedemy.eggeum.android.updatecafe.ui.item.CafeMenuItem
import us.wedemy.eggeum.android.updatecafe.viewmodel.SelectCafeMenuViewModel

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
          // TODO: cafeMenu 데이터 넘겨주기
          val action = SelectCafeMenuFragmentDirections.actionFragmentSelectCafeMenuToFragmentInputCafeMenu()
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

  private val viewModel by viewModels<SelectCafeMenuViewModel>()

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
          // TODO: 삭제 API 호출
          val cafeMenuList = viewModel.cafeMenuList.value.uiStateList
          val newCafeMenuList = mutableListOf<ProductEntity>()
          cafeMenuList?.forEach {
            if (!(it.name == item.name && it.price == item.price)) {
              newCafeMenuList.add(it.toEntity())
            }
          }
          viewModel.placeBody.menu?.products = newCafeMenuList
          val newPlaceBody = (viewModel.placeBody.menu?.products as MutableList<ProductEntity>)
          val changedUiState = viewModel.initializeUiState(products = newPlaceBody)
          viewModel.uiStateUpdateCafeMenuList(productList = changedUiState)
        }
      })
      .setNegativeButton("취소", null) // 취소 시에는 동작 없음.
      .show()
    dialog.apply {
      getButton(DialogInterface.BUTTON_POSITIVE)
        .setTextColor(ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.teal_500))
      getButton(DialogInterface.BUTTON_NEGATIVE)
        .setTextColor(ContextCompat.getColor(requireContext(), us.wedemy.eggeum.android.design.R.color.gray_400))
    }
  }

  private fun initView() {
    repeatOnStarted {
      launch {
        // TODO: 지도에서 placdId 받아와서 인자 넣기
        viewModel.getCafeMenuList(1)
      }
    }
    with(binding) {
      rvCafeMenuList.apply {
        setHasFixedSize(true)
        adapter = cafeMenuAdapter
      }
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
        // TODO: 새로운 placeBody -> UpsertPlaceBody -> db update -> 수정 완료 페이지
        viewModel.getUpdateCafeMenu()
        val action = SelectCafeMenuFragmentDirections.actionFragmentSelectCafeMenuToFragmentUpdateMenuComplete()
        findNavController().safeNavigate(action)
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.cafeMenuList.collect { cafeMenuList ->
          val cafeMenu = cafeMenuList.uiStateList?.map { it.toMain() }!!
          Timber.d("" + cafeMenu)
          cafeMenuAdapter.replaceAll(cafeMenu)
        }
      }
    }
  }
}
