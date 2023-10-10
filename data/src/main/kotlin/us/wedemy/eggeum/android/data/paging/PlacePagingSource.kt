/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.ktor.client.plugins.ResponseException
import java.io.IOException
import us.wedemy.eggeum.android.data.mapper.toDomain
import us.wedemy.eggeum.android.data.service.ApiService
import us.wedemy.eggeum.android.data.util.Constants.PAGING_SIZE
import us.wedemy.eggeum.android.data.util.Constants.STARTING_PAGE_INDEX
import us.wedemy.eggeum.android.domain.model.place.PlaceBody

public class PlacePagingSource(
  private val apiService: ApiService,
) : PagingSource<Int, PlaceBody>() {

  // key 의 초기값은 null, load 함수 참고
  // 페이지를 갱신 할때 수행 되는 함수
  override fun getRefreshKey(state: PagingState<Int, PlaceBody>): Int? {
    // 가장 최근의 접근한 page 를 state.anchorPosition 으로 받고
    // 그 주위의 페이지를 읽어 오도록 키를 반환 해주는 역할
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }

  // pager 가 데이터를 호출할 때마다 불리는 함수
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlaceBody> {
    return try {
      val pageNumber = params.key ?: STARTING_PAGE_INDEX
      val response = apiService.getPlaceList()
      // api response parameter 로 isEnd 를 제공해 줌 이 값을 통해 마지막 페이지인 여부를 판단할 수 있음
      val endOfPaginationReached = response.list.isEmpty()
      if (response.list.isNotEmpty()) {
        val prevKey = if (pageNumber == STARTING_PAGE_INDEX)
        // 첫번째 키 이기 때문에 그 전 key null
          null else pageNumber - 1
        val nextKey = if (endOfPaginationReached) {
          // 마지막 키이기 때문에 그 다음 key null
          null
        } else {
          pageNumber + (params.loadSize / PAGING_SIZE)
        }
        // 반환
        LoadResult.Page(
          data = response.list.map {placeBody ->
            placeBody.toDomain()
          },
          prevKey = prevKey,
          nextKey = nextKey
        )
      } else {
        // 반환
        LoadResult.Page(
          data = emptyList(),
          prevKey = null,
          nextKey = null,
        )
      }
    } catch (exception: IOException) {
      LoadResult.Error(exception)
    } catch (exception: ResponseException) {
      LoadResult.Error(exception)
    }
  }
}
