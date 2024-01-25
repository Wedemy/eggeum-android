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
import us.wedemy.eggeum.android.data.datasource.place.local.PlaceLocalDataSource
import us.wedemy.eggeum.android.data.datasource.place.remote.PlaceRemoteDataSource
import us.wedemy.eggeum.android.data.mapper.toModel
import us.wedemy.eggeum.android.data.mapper.toEntity
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceEntity
import us.wedemy.eggeum.android.domain.repository.PlaceRepository

public class PlaceRepositoryImpl @Inject constructor(
  private val remoteDataSource: PlaceRemoteDataSource,
  private val localDataSource: PlaceLocalDataSource,
) : PlaceRepository {
  override suspend fun getPlace(placeId: Long): PlaceEntity? {
    return remoteDataSource.getPlace(placeId)?.toEntity()
  }

  override fun getPlaceList(
    distance: Double?,
    endDate: String?,
    latitude: Double?,
    longitude: Double?,
    page: Int?,
    search: String?,
    size: Int?,
    sort: String?,
    startDate: String?,
    type: String?,
  ): Flow<PagingData<PlaceEntity>> {
    return remoteDataSource.getPlaceList(
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
    remoteDataSource.upsertPlace(upsertPlaceEntity.toModel())
  }

  override suspend fun insertRecentSearchPlace(placeEntity: PlaceEntity) {
    localDataSource.insertRecentSearchPlace(placeEntity)
  }

  override suspend fun deleteRecentSearchPlace(placeEntity: PlaceEntity) {
    localDataSource.deleteRecentSearchPlace(placeEntity)
  }

  override fun getRecentSearchPlaceList(): Flow<PagingData<PlaceEntity>> {
    return localDataSource.getRecentSearchPlaceList().map { pagingData ->
      pagingData.map { place ->
        place.toEntity()
      }
    }
  }
}
