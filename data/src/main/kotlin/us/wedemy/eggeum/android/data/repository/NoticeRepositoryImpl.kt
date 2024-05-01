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
import us.wedemy.eggeum.android.data.datasource.notice.NoticeDataSource
import us.wedemy.eggeum.android.data.mapper.toEntity
import us.wedemy.eggeum.android.domain.model.notice.NoticeEntity
import us.wedemy.eggeum.android.domain.repository.NoticeRepository

internal class NoticeRepositoryImpl @Inject constructor(
  private val dataSource: NoticeDataSource,
) : NoticeRepository {
  override suspend fun getNotice(noticeId: Long): NoticeEntity {
    return dataSource.getNotice(noticeId).toEntity()
  }

  override fun getNoticeList(
    search: String?,
    page: Int?,
    size: Int?,
    sort: String?,
    startDate: String?,
    endDate: String?,
  ): Flow<PagingData<NoticeEntity>> {
    return dataSource.getNoticeList(
      search = search,
      page = page,
      size = size,
      sort = sort,
      startDate = startDate,
      endDate = endDate,
    ).map { pagingData ->
      pagingData.map { notice ->
        notice.toEntity()
      }
    }
  }
}
