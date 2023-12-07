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
          showEditOrDeleteDialog()
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

  private fun showEditOrDeleteDialog() {
    val dialog = AlertDialog.Builder(requireContext())
      .setMessage("삭제 하시겠어요?")
      .setPositiveButton("삭제", object : OnClickListener {
        override fun onClick(p0: DialogInterface?, p1: Int) {
          // TODO: 삭제 API 호출
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
        // TODO: 그냥 다음으로 넘어가면, 어떤 동작을 해야 하나?
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
