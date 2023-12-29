/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceEntity

/** 장소 API */
public interface PlaceRepository {
  /**
   * 장소 단건 조회
   *
   * @param placeId 조회할 장소 아아디
   */
  public suspend fun getPlace(
    placeId: Int,
  ): PlaceEntity?

  /**
   * 장소 목록 조회
   *
   * @param distance 거리
   * @param endDate 종료일
   * @param latitude 위도
   * @param longitude 경도
   * @param page 요청 페이지
   * @param search 검색어
   * @param size 요청 row
   * @param sort 정렬(SortType)
   * @param startDate 시작일
   * @param type 타입(PlaceType)
   */
  public fun getPlaceList(
    distance: Int? = null,
    endDate: String? = null,
    latitude: Int? = null,
    longitude: Int? = null,
    page: Int? = null,
    search: String? = null,
    size: Int? = null,
    sort: String? = null,
    startDate: String? = null,
    type: String? = null,
  ): Flow<PagingData<PlaceEntity>>

  /**
   * 장소 추가/수정 요청
   *
   * @param upsertPlaceEntity
   */
  public suspend fun upsertPlace(upsertPlaceEntity: UpsertPlaceEntity)

  /**
   * 최근 검색 장소 추가
   *
   * @param placeEntity
   */
  public suspend fun insertRecentSearchPlace(placeEntity: PlaceEntity)

  /**
   * 최근 검색 장소 삭제
   *
   * @param placeEntity
   */
  public suspend fun deleteRecentSearchPlace(placeEntity: PlaceEntity)

  /**
   * 최근 검색 장소 조회
   */
  public fun getRecentSearchPlaceList(): Flow<PagingData<PlaceEntity>>
}
