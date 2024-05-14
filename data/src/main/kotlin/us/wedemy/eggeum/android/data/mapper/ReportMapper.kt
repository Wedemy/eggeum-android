/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.mapper

import us.wedemy.eggeum.android.data.model.report.CreateReportRequest
import us.wedemy.eggeum.android.data.model.report.UpdateReportRequest
import us.wedemy.eggeum.android.data.model.report.ReportListResponse
import us.wedemy.eggeum.android.data.model.report.ReportResponse
import us.wedemy.eggeum.android.domain.model.report.CreateReportEntity
import us.wedemy.eggeum.android.domain.model.report.ReportEntity
import us.wedemy.eggeum.android.domain.model.report.ReportListEntity
import us.wedemy.eggeum.android.domain.model.report.UpdateReportEntity

internal fun ReportResponse.toEntity() =
  ReportEntity(
    content = content,
    status = status,
    title = title,
    user = user.toEntity(),
  )

internal fun ReportListResponse.toEntity() =
  ReportListEntity(
    list = list.map(ReportResponse::toEntity),
    totalPages = totalPages,
    totalElements = totalElements,
  )

internal fun CreateReportEntity.toModel(): CreateReportRequest =
  CreateReportRequest(
    title = title,
    content = content,
  )

internal fun UpdateReportEntity.toModel(): UpdateReportRequest =
  UpdateReportRequest(
    title = title,
    content = content,
  )
