/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import us.wedemy.eggeum.android.data.datasource.report.ReportDataSource
import us.wedemy.eggeum.android.data.mapper.toModel
import us.wedemy.eggeum.android.data.mapper.toEntity
import us.wedemy.eggeum.android.domain.model.report.CreateReportEntity
import us.wedemy.eggeum.android.domain.model.report.ReportEntity
import us.wedemy.eggeum.android.domain.model.report.UpdateReportEntity
import us.wedemy.eggeum.android.domain.repository.ReportRepository

public class ReportRepositoryImpl @Inject constructor(
  private val dataSource: ReportDataSource,
) : ReportRepository {
  override suspend fun getReport(reportId: Int): ReportEntity? {
    return dataSource.getReport(reportId)?.toEntity()
  }

  override suspend fun getReportList(
    page: Int?,
    size: Int?,
    sort: String?,
  ): Flow<PagingData<ReportEntity>> {
    return dataSource.getReportList(
      page = page,
      size = size,
      sort = sort,
    ).map { pagingData ->
      pagingData.map { report ->
        report.toEntity()
      }
    }
  }

  public override suspend fun createReport(createReportEntity: CreateReportEntity) {
    dataSource.createReport(createReportEntity.toModel())
  }

  public override suspend fun updateReport(reportId: Int, updateReportEntity: UpdateReportEntity) {
    dataSource.updateReport(reportId, updateReportEntity.toModel())
  }
}
