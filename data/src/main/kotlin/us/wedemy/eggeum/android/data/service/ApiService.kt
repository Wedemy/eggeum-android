/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import us.wedemy.eggeum.android.data.client.jsonBody
import us.wedemy.eggeum.android.data.mapper.toData
import us.wedemy.eggeum.android.data.model.notice.NoticeBodyResponse
import us.wedemy.eggeum.android.data.model.notice.NoticeListResponse
import us.wedemy.eggeum.android.data.model.place.Image
import us.wedemy.eggeum.android.data.model.place.Info
import us.wedemy.eggeum.android.data.model.place.Menu
import us.wedemy.eggeum.android.data.model.place.PlaceBodyResponse
import us.wedemy.eggeum.android.data.model.place.PlaceListResponse
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceBody

@Singleton
public class ApiService @Inject constructor(
  @Named("AuthHttpClient")
  private val client: HttpClient,
  moshi: Moshi,
) {
  private val placeBodyAdapter = moshi.adapter<PlaceBodyResponse>()
  private val placeListAdapter = moshi.adapter<PlaceListResponse>()
  private val imageAdapter = moshi.adapter<Image>()
  private val infoAdapater = moshi.adapter<Info>()
  private val menuAdapter = moshi.adapter<Menu>()

  private val noticeBodyAdapter = moshi.adapter<NoticeBodyResponse>()
  private val noticeListAdapter = moshi.adapter<NoticeListResponse>()
  public suspend fun getPlaceBody(placeId: Int): PlaceBodyResponse? {
    val responseText =
      client
        .get("app/place/$placeId") {
          parameter("placeId", placeId)
        }
        .bodyAsText()
    return placeBodyAdapter.fromJson(responseText)
  }

  public suspend fun getPlaceList(
    distance: Int? = null,
    endDate: String? = null,
    latitude: Int? = null,
    longitude: Int? = null,
    page: Int? = null,
    search: String? = null,
    size: Int? = null,
    sort: String? = null,
    startDate: String? = null,
    type: String? = null,
  ): PlaceListResponse? {
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
    return placeListAdapter.fromJson(responseText)
  }

  public suspend fun upsertPlace(upsertPlaceBody: UpsertPlaceBody) {
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

  public suspend fun getNoticeBody(noticeId: Int): NoticeBodyResponse? {
    val responseText =
      client
        .get("notice/$noticeId")
        .bodyAsText()
    return noticeBodyAdapter.fromJson(responseText)
  }

  public suspend fun getNoticeList(
    search: String? = null,
    page: Int? = null,
    size: Int? = null,
    sort: String? = null,
    startDate: String? = null,
    endDate: String? = null,
  ): NoticeListResponse? {
    val responseText =
      client
        .get("notice/all") {
          parameter("search", search)
          parameter("page", page)
          parameter("size", size)
          parameter("sort", sort)
          parameter("startDate", startDate)
          parameter("endDate", endDate)
        }
        .bodyAsText()
    return noticeListAdapter.fromJson(responseText)
  }
}
