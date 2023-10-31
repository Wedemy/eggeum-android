/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.report

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import us.wedemy.eggeum.android.data.model.report.CreateReportRequest
import us.wedemy.eggeum.android.data.model.report.UpdateReportRequest
import us.wedemy.eggeum.android.data.model.report.ReportResponse

public interface ReportDataSource {
  public suspend fun getReport(reportId: Int): ReportResponse?

  public suspend fun getReportList(
    page: Int?,
    size: Int?,
    sort: String?,
  ): Flow<PagingData<ReportResponse>>

  public suspend fun createReport(createReportRequest: CreateReportRequest)

  public suspend fun updateReport(reportId: Int, updateReportRequest: UpdateReportRequest)
}
