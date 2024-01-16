/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException
import retrofit2.HttpException
import timber.log.Timber
import us.wedemy.eggeum.android.data.model.notice.NoticeResponse
import us.wedemy.eggeum.android.data.service.NoticeService
import us.wedemy.eggeum.android.data.util.Constants.PAGING_SIZE
import us.wedemy.eggeum.android.data.util.Constants.STARTING_PAGE_INDEX

public class NoticePagingSource(
  private val service: NoticeService,
  private val query: String? = null,
) : PagingSource<Int, NoticeResponse>() {

  public override fun getRefreshKey(state: PagingState<Int, NoticeResponse>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }

  public override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NoticeResponse> {
    return try {
      val pageNumber = params.key ?: STARTING_PAGE_INDEX
      val response = service.getNoticeList(page = pageNumber, search = query, size = params.loadSize)

      val endOfPaginationReached = response.list.isEmpty()

      LoadResult.Page(
        data = response.list,
        prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
        nextKey = if (endOfPaginationReached) {
          null
        } else {
          // initial load size = 3 * NETWORK_PAGE_SIZE
          // ensure we're not requesting duplicating items, at the 2nd request
          pageNumber + (params.loadSize / PAGING_SIZE)
        },
      )
    } catch (exception: IOException) {
      Timber.e(exception)
      LoadResult.Error(exception)
    } catch (exception: HttpException) {
      Timber.e(exception)
      LoadResult.Error(exception)
    }
  }
}
