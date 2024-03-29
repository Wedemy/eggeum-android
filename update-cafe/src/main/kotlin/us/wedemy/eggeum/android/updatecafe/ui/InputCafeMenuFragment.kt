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
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.base.BaseFragment
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentInputCafeMenuBinding
import us.wedemy.eggeum.android.updatecafe.ui.item.CafeMenuItem
import us.wedemy.eggeum.android.updatecafe.viewmodel.ProposeCafeInfoViewModel

@AndroidEntryPoint
class InputCafeMenuFragment : BaseFragment<FragmentInputCafeMenuBinding>() {
  override fun getViewBinding() = FragmentInputCafeMenuBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<ProposeCafeInfoViewModel>()

  private val args by navArgs<InputCafeMenuFragmentArgs>()

  private lateinit var newCafeMenuName: String
  private lateinit var newCafeMenuPrice: String

  private val beforeAndAfterCafeMenuItem = mutableMapOf<String, CafeMenuItem>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initView()
    initListener()
    initObserver()
  }

  override fun onStart() {
    super.onStart()
    beforeAndAfterCafeMenuItem["before"] = CafeMenuItem(name = args.cafeMenuItem.name, price = args.cafeMenuItem.price)
  }

  private fun initView() {
    binding.apply {
      tietInputCafeName.setText(args.cafeMenuItem.name)
      tietInputCafePrice.setText(args.cafeMenuItem.price.toString())

      newCafeMenuName = args.cafeMenuItem.name
      newCafeMenuPrice = args.cafeMenuItem.price.toString()
    }
  }

  private fun initListener() {
    with(binding) {
      tietInputCafeName.doAfterTextChanged {
        newCafeMenuName = it.toString()
      }

      tietInputCafePrice.doAfterTextChanged {
        newCafeMenuPrice = it.toString()
      }

      btnUpdateMenuComplete.setOnClickListener {
        // TODO: 데이터 변경 후, 이전 Fragment에 전달하기
        beforeAndAfterCafeMenuItem["after"] = CafeMenuItem(name = newCafeMenuName, price = newCafeMenuPrice.toInt())
        viewModel.setCafeMenuItemMap(cafeMenuItemMap = beforeAndAfterCafeMenuItem)

        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      tbSelectInputCafeMenu.setNavigationOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.showToastEvent.collect { message ->
          Toast.makeText(requireContext(), message.asString(requireContext()), Toast.LENGTH_SHORT).show()
        }
      }
    }

    repeatOnStarted {
      viewModel.navigateToLoginEvent.collect {
        (activity as UpdateCafeActivity).navigateToLogin()
      }
    }
  }
}
