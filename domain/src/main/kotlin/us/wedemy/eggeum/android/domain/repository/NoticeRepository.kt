/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.repository

import us.wedemy.eggeum.android.domain.model.notice.NoticeBody
import us.wedemy.eggeum.android.domain.model.notice.NoticeList

/** 공지사항 API */
public interface NoticeRepository {
  /**
   * 공지사항 단건 조회
   *
   * @param noticeId 조회할 공지 아이디
   */
  public suspend fun getNoticeBody(noticeId: Int): NoticeBody?

  /**
   * 조건에 맞는 공지 목록 조회
   *
   * @param search 공지 검색어
   * @param page 페이지네이션에 적용할 페이지
   * @param size 한 페이지당 불러올 공지 개수
   * @param sort 공지 목록 정렬 정책
   * @param startDate 공지 등록일 포함 시작 날짜
   * @param endDate 공지 등록일 포함 마지막 날짜
   */
  public suspend fun getNoticeList(
    search: String?,
    page: Int?,
    size: Int?,
    sort: String?,
    startDate: String?,
    endDate: String?,
  ): NoticeList?
}
