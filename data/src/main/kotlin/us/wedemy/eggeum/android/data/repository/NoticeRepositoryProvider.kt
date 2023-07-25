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
import javax.inject.Singleton
import us.wedemy.eggeum.android.data.mapper.toDomain
import us.wedemy.eggeum.android.data.model.notice.NoticeBodyResponse
import us.wedemy.eggeum.android.data.model.notice.NoticeListResponse
import us.wedemy.eggeum.android.domain.model.notice.NoticeBody
import us.wedemy.eggeum.android.domain.model.notice.NoticeList
import us.wedemy.eggeum.android.domain.repository.NoticeRepository

@Singleton
public class NoticeRepositoryProvider @Inject constructor(
  private val client: HttpClient,
  moshi: Moshi,
) : NoticeRepository {
  private val noticeBodyAdapter = moshi.adapter<NoticeBodyResponse>()
  private val noticeListAdapter = moshi.adapter<NoticeListResponse>()

  override suspend fun getNoticeBody(noticeId: Int): NoticeBody? {
    val responseText =
      client
        .get("notice/$noticeId")
        .bodyAsText()
    val response = noticeBodyAdapter.fromJson(responseText)
    return response?.toDomain()
  }

  override suspend fun getNoticeList(
    search: String?,
    page: Int?,
    size: Int?,
    sort: String?,
    startDate: String?,
    endDate: String?,
  ): NoticeList? {
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
    val response = noticeListAdapter.fromJson(responseText)
    return response?.toDomain()
  }
}
