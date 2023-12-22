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

public interface PlaceRemoteDataSource {
  public suspend fun getPlace(placeId: Int): PlaceResponse?

  public fun getPlaceList(
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
  ): Flow<PagingData<PlaceResponse>>

  public suspend fun upsertPlace(upsertPlaceRequest: UpsertPlaceRequest)
}
