/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import us.wedemy.eggeum.android.domain.model.report.CreateReportEntity
import us.wedemy.eggeum.android.domain.model.report.ReportEntity
import us.wedemy.eggeum.android.domain.model.report.UpdateReportEntity

/** 문의 하기 API */
public interface ReportRepository {
  /**
   * 사용자 문의 단건 조회
   */
  public suspend fun getReport(reportId: Long): ReportEntity?

  /**
   * 사용자 문의 목록 조회
   *
   * @param page 페이지네이션에 적용할 페이지
   * @param size 한 페이지당 불러올 문의 개수
   * @param sort 문의 목록 정렬 정책
   */
  public fun getReportList(
    page: Int? = null,
    size: Int? = null,
    sort: String? = null,
  ): Flow<PagingData<ReportEntity>>

  /**
   * 사용자 문의 하기
   *
   * @param content 문의 내용
   * @param title 문의 제목
   */
  public suspend fun createReport(createReportEntity: CreateReportEntity)

  /**
   * 유저 문의하기 수정
   *
   * @param reportId 수정할 문의 아이디
   * @param content 문의 내용
   * @param title 문의 제목
   */
  public suspend fun updateReport(
    reportId: Long,
    updateReportEntity: UpdateReportEntity,
  )
}
