/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import us.wedemy.eggeum.android.data.model.place.PlaceListResponse
import us.wedemy.eggeum.android.data.model.place.PlaceResponse
import us.wedemy.eggeum.android.data.model.place.UpsertPlaceRequest

internal interface PlaceService {

  @GET("app/place/{placeId}")
  suspend fun getPlace(
    @Path("placeId") placeId: Long,
  ): PlaceResponse

  @GET("app/place")
  suspend fun getPlaceList(
    @Query("distance") distance: Double? = null,
    @Query("endDate") endDate: String? = null,
    @Query("latitude") latitude: Double? = null,
    @Query("longitude") longitude: Double? = null,
    @Query("page") page: Int? = null,
    @Query("search") search: String? = null,
    @Query("size") size: Int? = null,
    @Query("sort") sort: String? = null,
    @Query("startDate") startDate: String? = null,
    @Query("type") type: String? = null,
  ): PlaceListResponse

  @POST("app/place/edits")
  suspend fun upsertPlace(
    @Body upsertPlaceRequest: UpsertPlaceRequest,
  )
}
