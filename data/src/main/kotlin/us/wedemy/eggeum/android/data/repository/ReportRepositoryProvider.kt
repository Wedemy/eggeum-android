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
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import javax.inject.Named
import us.wedemy.eggeum.android.data.client.jsonBody
import us.wedemy.eggeum.android.data.mapper.toDomain
import us.wedemy.eggeum.android.data.model.report.ReportBodyResponse
import us.wedemy.eggeum.android.data.model.report.ReportListResponse
import us.wedemy.eggeum.android.domain.model.report.ReportBody
import us.wedemy.eggeum.android.domain.model.report.ReportList
import us.wedemy.eggeum.android.domain.repository.ReportRepository

public class ReportRepositoryProvider @Inject constructor(
  @Named("AuthHttpClient")
  private val client: HttpClient,
  moshi: Moshi,
) : ReportRepository {
  private val reportBodyAdapter = moshi.adapter<ReportBodyResponse>()
  private val reportListAdapter = moshi.adapter<ReportListResponse>()

  override suspend fun getReportBody(reportId: Int): ReportBody? {
    val responseText =
      client
        .get("app/report/$reportId")
        .bodyAsText()
    val response = reportBodyAdapter.fromJson(responseText)
    return response?.toDomain()
  }

  override suspend fun getReportList(
    page: Int?,
    size: Int?,
    sort: String?,
  ): ReportList? {
    val responseText =
      client
        .get("app/report/list") {
          parameter("page", page)
          parameter("size", size)
          parameter("sort", sort)
        }
        .bodyAsText()
    val response = reportListAdapter.fromJson(responseText)
    return response?.toDomain()
  }

  override suspend fun createReport(
    content: String,
    title: String,
  ) {
    client
      .post("app/report/set") {
        jsonBody {
          "content" withString content
          "title" withString title
        }
      }
  }

  override suspend fun updateReport(
    reportId: Int,
    content: String,
    title: String,
  ) {
    client
      .patch("app/report/update/$reportId") {
        jsonBody {
          "content" withString content
          "title" withString title
        }
      }
  }
}
