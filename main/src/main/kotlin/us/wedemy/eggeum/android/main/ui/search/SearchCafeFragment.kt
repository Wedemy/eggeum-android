/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.addDivider
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.safeNavigate
import us.wedemy.eggeum.android.common.extension.textChangesAsFlow
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.design.R
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.main.databinding.FragmentSearchCafeBinding
import us.wedemy.eggeum.android.main.mapper.toUiModel
import us.wedemy.eggeum.android.main.ui.adapter.SearchCafeAdapter
import us.wedemy.eggeum.android.main.ui.adapter.listener.SearchCafeClickListener
import us.wedemy.eggeum.android.main.viewmodel.CafeDetailViewModel
import us.wedemy.eggeum.android.main.viewmodel.SearchCafeViewModel

@AndroidEntryPoint
class SearchCafeFragment : BaseFragment<FragmentSearchCafeBinding>() {
  override fun getViewBinding() = FragmentSearchCafeBinding.inflate(layoutInflater)

  private val cafeDetailViewModel by activityViewModels<CafeDetailViewModel>()
  private val searchCafeViewModel by viewModels<SearchCafeViewModel>()

  private val searchCafeAdapter by lazy {
    SearchCafeAdapter(
      object : SearchCafeClickListener {
        override fun onItemClick(item: PlaceEntity) {
          searchCafeViewModel.insertRecentSearchPlace(item)

          cafeDetailViewModel.setCafeDetailInfo(item.toUiModel())
          val action = SearchCafeFragmentDirections.actionFragmentSearchCafeToFragmentMap()
          findNavController().safeNavigate(action)
        }
      },
    )
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initListener()
    initObserver()
  }

  private fun initView() {
    binding.rvSearchCafe.apply {
      setHasFixedSize(true)
      addDivider(R.color.gray_300)
      adapter = searchCafeAdapter
    }
  }

  private fun initListener() {
    binding.tilSearchCafe.setStartIconOnClickListener {
      if (!findNavController().navigateUp()) {
        requireActivity().finish()
      }
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        searchCafeViewModel.searchPlaceList.collectLatest {
          searchCafeAdapter.submitData(it)
        }
      }

      launch {
        val editTextFlow = binding.tietSearchCafe.textChangesAsFlow()
        editTextFlow.collect { text ->
            val query = text.toString().trim()
            searchCafeViewModel.setSearchQuery(query)
          }
      }
    }
  }
}
