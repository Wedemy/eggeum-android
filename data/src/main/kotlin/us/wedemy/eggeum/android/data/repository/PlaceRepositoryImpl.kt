/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import us.wedemy.eggeum.android.data.datasource.place.PlaceDataSource
import us.wedemy.eggeum.android.data.mapper.toModel
import us.wedemy.eggeum.android.data.mapper.toEntity
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceEntity
import us.wedemy.eggeum.android.domain.repository.PlaceRepository

public class PlaceRepositoryImpl @Inject constructor(
  private val dataSource: PlaceDataSource,
) : PlaceRepository {
  override suspend fun getPlace(placeId: Int): PlaceEntity? {
    return dataSource.getPlace(placeId)?.toEntity()
  }

  override fun getPlaceList(
    distance: Int?,
    endDate: String?,
    latitude: Int?,
    longitude: Int?,
    page: Int?,
    search: String?,
    size: Int?,
    sort: String?,
    startDate: String?,
    type: String?,
  ): Flow<PagingData<PlaceEntity>> {
    return dataSource.getPlaceList(
      distance = distance,
      endDate = endDate,
      latitude = latitude,
      longitude = longitude,
      page = page,
      search = search,
      size = size,
      sort = sort,
      startDate = startDate,
      type = type,
    ).map { pagingData ->
      pagingData.map { place ->
        place.toEntity()
      }
    }
  }

  override suspend fun upsertPlace(upsertPlaceEntity: UpsertPlaceEntity) {
    dataSource.upsertPlace(upsertPlaceEntity.toModel())
  }
}
