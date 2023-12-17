/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import us.wedemy.eggeum.android.domain.usecase.GetPlaceListUseCase

@HiltViewModel
class SearchViewModel @Inject constructor(
  getPlaceListUseCase: GetPlaceListUseCase,
) : ViewModel() {

  val placeList = getPlaceListUseCase().cachedIn(viewModelScope)
}
