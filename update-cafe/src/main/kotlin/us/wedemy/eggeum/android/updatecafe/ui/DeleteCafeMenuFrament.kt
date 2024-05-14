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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import us.wedemy.eggeum.android.common.base.BaseDialogFragment
import us.wedemy.eggeum.android.domain.model.place.ProductEntity
import us.wedemy.eggeum.android.updatecafe.databinding.FragmentDeleteCafeMenuBinding
import us.wedemy.eggeum.android.updatecafe.ui.item.CafeMenuItem
import us.wedemy.eggeum.android.updatecafe.viewmodel.ProposeCafeInfoViewModel

@AndroidEntryPoint
class DeleteCafeMenuFrament : BaseDialogFragment<FragmentDeleteCafeMenuBinding>() {
  override fun getViewBinding() = FragmentDeleteCafeMenuBinding.inflate(layoutInflater)

  private val viewModel by activityViewModels<ProposeCafeInfoViewModel>()

  private val args by navArgs<InputCafeMenuFragmentArgs>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initListener()
  }

  private fun initListener() {
    with(binding) {
      btnCancelCafeMenu.setOnClickListener {
        if (!findNavController().navigateUp()) {
          requireActivity().finish()
        }
      }

      btnDeleteCafeMenu.setOnClickListener {
        val newCafeMenuList = mutableListOf<CafeMenuItem>()
        val cafeMenuList = viewModel.cafeMenuList.value
        cafeMenuList.forEach {
          if (!(it.name == args.cafeMenuItem.name && it.price == args.cafeMenuItem.price)) {
            newCafeMenuList.add(it)
          }
        }
        viewModel.placeBody.menu?.products = newCafeMenuList.map { it.toEntity() }
        val newPlaceBody = (viewModel.placeBody.menu?.products as MutableList<ProductEntity>)
        val newCafeMenuItemList = viewModel.initializeCafeMenuItem(products = newPlaceBody)
        viewModel.updateCafeMenuList(cafeMenuItemList = newCafeMenuItemList)

        findNavController().navigateUp()
      }
    }
  }
}
