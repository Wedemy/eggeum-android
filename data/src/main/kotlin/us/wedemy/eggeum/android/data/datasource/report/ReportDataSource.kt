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

internal interface ReportDataSource {
  suspend fun getReport(reportId: Long): ReportResponse

  fun getReportList(
    page: Int?,
    size: Int?,
    sort: String?,
  ): Flow<PagingData<ReportResponse>>

  suspend fun createReport(createReportRequest: CreateReportRequest)

  suspend fun updateReport(reportId: Long, updateReportRequest: UpdateReportRequest)
}
