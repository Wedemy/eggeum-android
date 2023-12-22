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
import us.wedemy.eggeum.android.data.local.db.PlaceSearchDatabase
import us.wedemy.eggeum.android.data.mapper.toModel
import us.wedemy.eggeum.android.data.model.place.PlaceResponse
import us.wedemy.eggeum.android.data.util.Constants
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity

public class PlaceLocalDataSourceImpl @Inject constructor(
  private val placeSearchDatabase: PlaceSearchDatabase,
) : PlaceLocalDataSource {
  override suspend fun insertPlace(placeEntity: PlaceEntity) {
    placeSearchDatabase.placeSearchDao().insertPlace(placeEntity.toModel())
  }

  override suspend fun deletePlace(placeEntity: PlaceEntity) {
    placeSearchDatabase.placeSearchDao().deletePlace(placeEntity.toModel())
  }

  override fun searchPlace(name: String): Flow<PagingData<PlaceResponse>> {
    val pagingSourceFactory = {
      placeSearchDatabase.placeSearchDao().searchPlace()
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
