/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.place.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import us.wedemy.eggeum.android.data.database.PlaceSearchDatabase
import us.wedemy.eggeum.android.data.mapper.toModel
import us.wedemy.eggeum.android.data.model.place.PlaceResponse
import us.wedemy.eggeum.android.data.util.Constants
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity

public class PlaceLocalDataSourceImpl @Inject constructor(
  private val placeSearchDatabase: PlaceSearchDatabase,
) : PlaceLocalDataSource {
  override suspend fun insertRecentSearchPlace(placeEntity: PlaceEntity) {
    placeSearchDatabase.placeSearchDao().insertRecentSearchPlace(placeEntity.toModel())
  }

  override suspend fun deleteRecentSearchPlace(placeEntity: PlaceEntity) {
    placeSearchDatabase.placeSearchDao().deleteRecentSearchPlace(placeEntity.toModel())
  }

  override fun getRecentSearchPlaceList(name: String?): Flow<PagingData<PlaceResponse>> {
    val pagingSourceFactory = {
      placeSearchDatabase.placeSearchDao().getRecentSearchPlaceList()
    }

    return Pager(
      config = PagingConfig(
        pageSize = Constants.PAGING_SIZE,
        enablePlaceholders = false,
      ),
      pagingSourceFactory = pagingSourceFactory,
    ).flow
  }
}
