/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.usecase

import androidx.paging.PagingData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import us.wedemy.eggeum.android.domain.model.report.ReportEntity
import us.wedemy.eggeum.android.domain.repository.ReportRepository
import us.wedemy.eggeum.android.domain.util.ReportApiResponseIsNull
import us.wedemy.eggeum.android.domain.util.runSuspendCatching

@Singleton
public class GetReportUseCase @Inject constructor(
  private val repository: ReportRepository,
) {
  public suspend operator fun invoke(reportId: Int): Result<ReportEntity> =
    runSuspendCatching {
      repository.getReport(reportId) ?: throw ReportApiResponseIsNull
    }
}

@Singleton
public class GetReportListUseCase @Inject constructor(
  private val repository: ReportRepository,
) {
  public operator fun invoke(): Flow<PagingData<ReportEntity>> {
    return repository.getReportList()
  }
}
