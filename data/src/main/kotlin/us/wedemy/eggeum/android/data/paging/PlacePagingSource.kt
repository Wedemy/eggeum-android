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
import us.wedemy.eggeum.android.data.model.place.PlaceResponse
import us.wedemy.eggeum.android.data.service.PlaceService
import us.wedemy.eggeum.android.data.util.Constants.PAGING_SIZE
import us.wedemy.eggeum.android.data.util.Constants.STARTING_PAGE_INDEX

public class PlacePagingSource(
  private val service: PlaceService,
) : PagingSource<Int, PlaceResponse>() {

  override fun getRefreshKey(state: PagingState<Int, PlaceResponse>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaceResponse> {
    return try {
      val pageNumber = params.key ?: STARTING_PAGE_INDEX
      val response = service.getPlaceList(page = pageNumber, size = params.loadSize)

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
