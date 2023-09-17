/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import javax.inject.Named
import us.wedemy.eggeum.android.data.client.jsonBody
import us.wedemy.eggeum.android.data.mapper.toData
import us.wedemy.eggeum.android.data.mapper.toDomain
import us.wedemy.eggeum.android.data.model.place.Image
import us.wedemy.eggeum.android.data.model.place.Info
import us.wedemy.eggeum.android.data.model.place.Menu
import us.wedemy.eggeum.android.data.model.place.PlaceBodyResponse
import us.wedemy.eggeum.android.data.model.place.PlaceListResponse
import us.wedemy.eggeum.android.domain.model.place.PlaceBody
import us.wedemy.eggeum.android.domain.model.place.PlaceList
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceBody
import us.wedemy.eggeum.android.domain.repository.PlaceRepository

public class PlaceRepositoryProvider @Inject constructor(
  @Named("AuthHttpClient")
  private val client: HttpClient,
  moshi: Moshi,
) : PlaceRepository {
  private val placeBodyAdapter = moshi.adapter<PlaceBodyResponse>()
  private val placeListAdapter = moshi.adapter<PlaceListResponse>()
  private val imageAdapter = moshi.adapter<Image>()
  private val infoAdapater = moshi.adapter<Info>()
  private val menuAdapter = moshi.adapter<Menu>()

  override suspend fun getPlaceBody(placeId: Int): PlaceBody? {
    val responseText =
      client
        .get("app/place/$placeId") {
          parameter("placeId", placeId)
        }
        .bodyAsText()
    val response = placeBodyAdapter.fromJson(responseText)
    return response?.toDomain()
  }

  override suspend fun getPlaceList(
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
  ): PlaceList? {
    val responseText =
      client
        .get("app/place") {
          parameter("distance", distance)
          parameter("endDate", endDate)
          parameter("latitude", latitude)
          parameter("longitude", longitude)
          parameter("page", page)
          parameter("search", search)
          parameter("size", size)
          parameter("sort", sort)
          parameter("startDate", startDate)
          parameter("type", type)
        }
        .bodyAsText()
    val response = placeListAdapter.fromJson(responseText)
    return response?.toDomain()
  }

  override suspend fun upsertPlace(upsertPlaceBody: UpsertPlaceBody) {
    client
      .get("app/place/edits") {
        jsonBody {
          "address1" withString upsertPlaceBody.address1
          "address2" withString upsertPlaceBody.address2
          "image".withPojo(imageAdapter, upsertPlaceBody.image.toData())
          "info".withPojo(infoAdapater, upsertPlaceBody.info.toData())
          "latitude" withDouble upsertPlaceBody.latitude
          "longitude" withDouble upsertPlaceBody.longitude
          "menu".withPojo(menuAdapter, upsertPlaceBody.menu.toData())
          "name" withString upsertPlaceBody.name
          "placeId" withInt upsertPlaceBody.placeId
          "remarks" withString upsertPlaceBody.remarks
          "type" withString upsertPlaceBody.type
        }
      }
  }
}
