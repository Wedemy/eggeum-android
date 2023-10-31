/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.report

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import us.wedemy.eggeum.android.data.model.report.CreateReportRequest
import us.wedemy.eggeum.android.data.model.report.UpdateReportRequest
import us.wedemy.eggeum.android.data.model.report.ReportResponse
import us.wedemy.eggeum.android.data.paging.ReportPagingSource
import us.wedemy.eggeum.android.data.service.ReportService
import us.wedemy.eggeum.android.data.util.Constants
import us.wedemy.eggeum.android.data.util.safeRequest

public class ReportDataSourceImpl @Inject constructor(
  private val service: ReportService,
) : ReportDataSource {

  public override suspend fun getReport(reportId: Int): ReportResponse? {
    return safeRequest {
      service.getReport(reportId)
    }
  }

  override suspend fun getReportList(
    page: Int?,
    size: Int?,
    sort: String?,
  ): Flow<PagingData<ReportResponse>> {
    val pagingSourceFactory = { ReportPagingSource(service) }

    return Pager(
      config = PagingConfig(
        pageSize = Constants.PAGING_SIZE,
        enablePlaceholders = false,
        maxSize = Constants.PAGING_SIZE * 3,
      ),
      pagingSourceFactory = pagingSourceFactory,
    ).flow
  }

  public override suspend fun createReport(createReportRequest: CreateReportRequest) {
    safeRequest {
      service.createReport(
        createReportRequest = createReportRequest,
      )
    }
  }

  public override suspend fun updateReport(reportId: Int, updateReportRequest: UpdateReportRequest) {
    safeRequest {
      service.updateReport(
        reportId = reportId,
        updateReportRequest = updateReportRequest,
      )
    }
  }
}
