/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.notice

/**
 * @param list 조회된 공지 목록
 * @param totalPages 페이지네이션 가능한 전체 페이지 수
 * @param totalElements 조회 가능한 전체 공지 수
 */
public data class NoticeListEntity(
  val list: List<NoticeEntity>,
  val totalPages: Int,
  val totalElements: Int,
)
