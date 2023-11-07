/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("unused", "UnusedPrivateProperty")

package us.wedemy.eggeum.android.updatecafe.ui

import android.os.Bundle
import android.view.View
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
import us.wedemy.eggeum.android.updatecafe.viewmodel.SelectCafeMenuViewModel

@AndroidEntryPoint
class SelectCafeMenuFragment : BaseFragment<FragmentSelectCafeMenuBinding>() {
  private val cafeMenuAdapter by lazy { CafeMenuAdapter() }

  override fun getViewBinding() = FragmentSelectCafeMenuBinding.inflate(layoutInflater)

  private val viewModel by viewModels<SelectCafeMenuViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    repeatOnStarted {
      launch {
        /**
         * Intent에서 placeId 가져오기
         */
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
        val action = SelectCafeMenuFragmentDirections.actionFragmentSelectCafeMenuToFragmentInputCafeMenu()
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
