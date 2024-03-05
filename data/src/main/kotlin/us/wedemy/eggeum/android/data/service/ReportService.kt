/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import us.wedemy.eggeum.android.data.model.report.CreateReportRequest
import us.wedemy.eggeum.android.data.model.report.ReportListResponse
import us.wedemy.eggeum.android.data.model.report.ReportResponse
import us.wedemy.eggeum.android.data.model.report.UpdateReportRequest

public interface ReportService {

  @GET("app/report/{reportId}")
  public suspend fun getReport(
    @Path("reportId") reportId: Long,
  ): ReportResponse

  @GET("app/report/list")
  public suspend fun getReportList(
    @Query("page") page: Int? = null,
    @Query("size") size: Int? = null,
    @Query("sort") sort: String? = null,
  ): ReportListResponse

  @POST("app/report/set")
  public suspend fun createReport(
    @Body createReportRequest: CreateReportRequest,
  )

  @PATCH("app/report/update/{reportId}")
  public suspend fun updateReport(
    @Path("reportId") reportId: Long,
    @Body updateReportRequest: UpdateReportRequest,
  )
}
