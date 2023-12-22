/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import us.wedemy.eggeum.android.domain.usecase.DeleteRecentSearchPlaceUseCase
import us.wedemy.eggeum.android.domain.usecase.GetPlaceListUseCase
import us.wedemy.eggeum.android.domain.usecase.GetRecentSearchPlaces
import us.wedemy.eggeum.android.domain.usecase.InsertRecentSearchPlaceUseCase

@HiltViewModel
class SearchCafeViewModel @Inject constructor(
  private val getPlaceListUseCase: GetPlaceListUseCase,
  private val insertRecentSearchPlaceUseCase: InsertRecentSearchPlaceUseCase,
  private val deleteRecentSearchPlaceUseCase: DeleteRecentSearchPlaceUseCase,
  private val getRecentSearchPlaces: GetRecentSearchPlaces,
) : ViewModel() {

  val placeList = getPlaceListUseCase().cachedIn(viewModelScope)
}