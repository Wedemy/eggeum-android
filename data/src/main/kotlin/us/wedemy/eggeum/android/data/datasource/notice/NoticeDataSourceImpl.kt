/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.notice

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import us.wedemy.eggeum.android.data.model.notice.NoticeResponse
import us.wedemy.eggeum.android.data.paging.NoticePagingSource
import us.wedemy.eggeum.android.data.service.NoticeService
import us.wedemy.eggeum.android.data.util.Constants
import us.wedemy.eggeum.android.data.util.safeRequest

public class NoticeDataSourceImpl @Inject constructor(
  private val service: NoticeService,
) : NoticeDataSource {
  override suspend fun getNotice(noticeId: Int): NoticeResponse? {
    return safeRequest {
      service.getNotice(noticeId)
    }
  }

  override fun getNoticeList(
    search: String?,
    page: Int?,
    size: Int?,
    sort: String?,
    startDate: String?,
    endDate: String?,
  ): Flow<PagingData<NoticeResponse>> {
    val pagingSourceFactory = { NoticePagingSource(service) }

    return Pager(
      config = PagingConfig(
        pageSize = Constants.PAGING_SIZE,
        enablePlaceholders = false,
      ),
      pagingSourceFactory = pagingSourceFactory,
    ).flow
  }
}
