/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import us.wedemy.eggeum.android.data.model.place.PlaceResponse
import us.wedemy.eggeum.android.data.model.place.PlaceListResponse
import us.wedemy.eggeum.android.data.model.place.UpsertPlaceRequest

public interface PlaceService {

  @GET("app/place/{placeId}")
  public suspend fun getPlace(
    @Path("placeId") placeId: Long,
  ): Response<PlaceResponse>

  @GET("app/place")
  public suspend fun getPlaceList(
    @Query("distance") distance: Int? = null,
    @Query("endDate") endDate: String? = null,
    @Query("latitude") latitude: Int? = null,
    @Query("longitude") longiture: Int? = null,
    @Query("page") page: Int? = null,
    @Query("search") search: String? = null,
    @Query("size") size: Int? = null,
    @Query("sort") sort: String? = null,
    @Query("startDate") startDate: String? = null,
    @Query("type") type: String? = null,
  ): PlaceListResponse

  @POST("app/place/edits")
  public suspend fun upsertPlace(
    @Body upsertPlaceRequest: UpsertPlaceRequest,
  ): Response<Unit>
}
