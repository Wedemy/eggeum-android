/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import us.wedemy.eggeum.android.common.extension.addDivider
import us.wedemy.eggeum.android.common.extension.repeatOnStarted
import us.wedemy.eggeum.android.common.extension.textChangesAsFlow
import us.wedemy.eggeum.android.common.ui.BaseFragment
import us.wedemy.eggeum.android.design.R
import us.wedemy.eggeum.android.main.databinding.FragmentSearchCafeBinding
import us.wedemy.eggeum.android.main.ui.adapter.SearchCafeAdapter
import us.wedemy.eggeum.android.main.ui.adapter.listener.SearchCafeClickListener
import us.wedemy.eggeum.android.main.viewmodel.SearchCafeViewModel

@AndroidEntryPoint
class SearchCafeFragment : BaseFragment<FragmentSearchCafeBinding>() {
  override fun getViewBinding() = FragmentSearchCafeBinding.inflate(layoutInflater)

  private val viewModel by viewModels<SearchCafeViewModel>()

  private val searchCafeAdapter by lazy {
    SearchCafeAdapter(
      object : SearchCafeClickListener {
        override fun onItemClick(position: Int) {
          Toast.makeText(requireContext(), "${position}번째 장소를 저장했습니다.", Toast.LENGTH_SHORT).show()
        }
      }
    )
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
    initObserver()

//    val action = SearchCafeFragmentDirections.actionFragmentSearchCafeToFragmentCafeDetail()
//    findNavController().safeNavigate(action)
  }

  private fun initView() {
    binding.rvSearchCafe.apply {
      setHasFixedSize(true)
      addDivider(R.color.gray_300)
      adapter = searchCafeAdapter
    }
  }

  private fun initObserver() {
    repeatOnStarted {
      launch {
        viewModel.searchPlaceList.collectLatest {
          searchCafeAdapter.submitData(it)
        }
      }

      launch {
        val editTextFlow = binding.tietSearchCafe.textChangesAsFlow()
        editTextFlow
          .collect { text ->
            val query = text.toString().trim()
            viewModel.setSearchQuery(query)
          }
      }
    }
  }
}
