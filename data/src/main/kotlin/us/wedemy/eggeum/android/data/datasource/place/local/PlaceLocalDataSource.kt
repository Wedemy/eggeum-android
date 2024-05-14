/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.place.local

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import us.wedemy.eggeum.android.data.model.place.PlaceResponse
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity

internal interface PlaceLocalDataSource {
  suspend fun insertRecentSearchPlace(placeEntity: PlaceEntity)

  suspend fun deleteRecentSearchPlace(placeEntity: PlaceEntity)

  fun getRecentSearchPlaceList(): Flow<PagingData<PlaceResponse>>
}
