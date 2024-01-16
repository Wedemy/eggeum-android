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
import us.wedemy.eggeum.android.domain.model.notice.NoticeEntity
import us.wedemy.eggeum.android.domain.repository.NoticeRepository
import us.wedemy.eggeum.android.domain.util.NoticeApiResponseIsNull
import us.wedemy.eggeum.android.domain.util.runSuspendCatching

@Singleton
public class GetNoticeUseCase @Inject constructor(
  private val repository: NoticeRepository,
) {
  public suspend operator fun invoke(noticeId: Long): Result<NoticeEntity> =
    runSuspendCatching {
      repository.getNotice(noticeId) ?: throw NoticeApiResponseIsNull
    }
}

@Singleton
public class GetNoticeListUseCase @Inject constructor(
  private val repository: NoticeRepository,
) {
  public operator fun invoke(query: String? = null): Flow<PagingData<NoticeEntity>> {
    return repository.getNoticeList(search = query)
  }
}
