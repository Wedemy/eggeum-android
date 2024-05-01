/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.place.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import us.wedemy.eggeum.android.data.model.place.PlaceResponse
import us.wedemy.eggeum.android.data.model.place.UpsertPlaceRequest
import us.wedemy.eggeum.android.data.paging.PlacePagingSource
import us.wedemy.eggeum.android.data.service.PlaceService
import us.wedemy.eggeum.android.data.util.Constants

internal class PlaceRemoteDataSourceImpl @Inject constructor(
  private val service: PlaceService,
) : PlaceRemoteDataSource {
  override suspend fun getPlace(placeId: Long): PlaceResponse {
    return service.getPlace(placeId)
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
  ): Flow<PagingData<PlaceResponse>> {
    val pagingSourceFactory = {
      PlacePagingSource(
        service = service,
        query = search,
        distance = distance,
        latitude = latitude,
        longitude = longitude,
      )
    }

    return Pager(
      config = PagingConfig(
        pageSize = Constants.PAGING_SIZE,
        enablePlaceholders = false,
      ),
      pagingSourceFactory = pagingSourceFactory,
    ).flow
  }

  override suspend fun upsertPlace(upsertPlaceRequest: UpsertPlaceRequest) {
    service.upsertPlace(upsertPlaceRequest)
  }
}
