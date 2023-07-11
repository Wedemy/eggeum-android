/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.usecase

import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.domain.model.notice.NoticeBody
import us.wedemy.eggeum.android.domain.model.notice.NoticeList
import us.wedemy.eggeum.android.domain.repository.NoticeRepository
import us.wedemy.eggeum.android.domain.util.runSuspendCatching

private val NoticeApiResponseIsNull = IOException("The Notice API response is null.")

@Singleton
public class GetNoticeBodyUseCase @Inject constructor(
  private val repository: NoticeRepository,
) {
  public suspend fun execute(noticeId: Int): Result<NoticeBody> =
    runSuspendCatching {
      repository.getNoticeBody(noticeId) ?: throw NoticeApiResponseIsNull
    }
}

@Singleton
public class GetNoticeListUseCase @Inject constructor(
  private val repository: NoticeRepository,
) {
  public suspend fun execute(
    search: String? = null,
    page: Int? = null,
    size: Int? = null,
    sort: String? = null,
    startDate: String? = null,
    endDate: String? = null,
  ): Result<NoticeList> =
    runSuspendCatching {
      repository
        .getNoticeList(
          search = search,
          page = page,
          size = size,
          sort = sort,
          startDate = startDate,
          endDate = endDate,
        ) ?: throw NoticeApiResponseIsNull
    }
}
