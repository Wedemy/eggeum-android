/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import us.wedemy.eggeum.android.data.mapper.toDomain
import us.wedemy.eggeum.android.data.paging.NoticePagingSource
import us.wedemy.eggeum.android.data.service.ApiService
import us.wedemy.eggeum.android.data.util.Constants
import us.wedemy.eggeum.android.domain.model.notice.NoticeBody
import us.wedemy.eggeum.android.domain.repository.NoticeRepository

@Singleton
public class NoticeRepositoryProvider @Inject constructor(
  private val apiService: ApiService,
) : NoticeRepository {
  override suspend fun getNoticeBody(noticeId: Int): NoticeBody? {
    return apiService.getNoticeBody(noticeId)?.toDomain()
  }

  override suspend fun getNoticeList(
    search: String?,
    page: Int?,
    size: Int?,
    sort: String?,
    startDate: String?,
    endDate: String?,
  ): Flow<PagingData<NoticeBody>> {
    val pagingSourceFactory = { NoticePagingSource(apiService) }

    return Pager(
      // pager 를 구현하기 위해서는
      // pagingConfig 를 통해 parameter 를 전달 해줘야함
      config = PagingConfig(
        // 어떤 기기로 동작 시키든 뷰홀더에 표시할 데이터가 모자르지 않을 정도의 값으로 설정
        pageSize = Constants.PAGING_SIZE,
        // true -> repository 의 전체 데이터 사이즈를 받아와서 recyclerview 의 placeholder 를 미리 만들어 놓음
        // 화면에 표시 되지 않는 항목은 null로 표시
        // 필요할 때 필요한 만큼만 로딩 하려면 false
        enablePlaceholders = false,
        // 페이저가 메모리에 가지고 있을 수 있는 최대 개수, 페이지 사이즈의 2~3배 정도
        maxSize = Constants.PAGING_SIZE * 3
      ),
      // api 호출 결과를 팩토리에 전달
      pagingSourceFactory = pagingSourceFactory
      // 결과를 flow 로 변환
    ).flow
  }
}

