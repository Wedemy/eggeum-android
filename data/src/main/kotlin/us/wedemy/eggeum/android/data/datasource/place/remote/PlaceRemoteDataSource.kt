/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.place.remote

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import us.wedemy.eggeum.android.data.model.place.PlaceResponse
import us.wedemy.eggeum.android.data.model.place.UpsertPlaceRequest

internal interface PlaceRemoteDataSource {
  suspend fun getPlace(placeId: Long): PlaceResponse

  fun getPlaceList(
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
  ): Flow<PagingData<PlaceResponse>>

  suspend fun upsertPlace(upsertPlaceRequest: UpsertPlaceRequest)
}
