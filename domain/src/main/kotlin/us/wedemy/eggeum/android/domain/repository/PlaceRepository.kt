/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.repository

import us.wedemy.eggeum.android.domain.model.place.PlaceBody
import us.wedemy.eggeum.android.domain.model.place.PlaceList
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceBody

/** 장소 API */
public interface PlaceRepository {
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
  public suspend fun getPlaceList(
    distance: Int?,
    endDate: String?,
    latitude: Int?,
    longitude: Int?,
    page: Int?,
    search: String?,
    size: Int?,
    sort: String?,
    startDate: String?,
    type: String?,
  ): PlaceList?

  /**
   * 장소 단건 조회
   *
   * @param placeId 조회할 장소 아아디
   */
  public suspend fun getPlaceBody(
    placeId: Int,
  ): PlaceBody?

  /**
   * 장소 추가/수정 요청
   *
   * @param upsertPlaceBody
   */
  public suspend fun upsertPlace(upsertPlaceBody: UpsertPlaceBody)
}
