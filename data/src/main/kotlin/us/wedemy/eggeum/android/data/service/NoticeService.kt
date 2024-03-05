/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import us.wedemy.eggeum.android.data.model.notice.NoticeListResponse
import us.wedemy.eggeum.android.data.model.notice.NoticeResponse

public interface NoticeService {

  @GET("app/notice/{noticeId}")
  public suspend fun getNotice(
    @Path("noticeId") noticeId: Long,
  ): NoticeResponse

  @GET("app/notice/all")
  public suspend fun getNoticeList(
    @Query("search") search: String? = null,
    @Query("page") page: Int? = null,
    @Query("size") size: Int? = null,
    @Query("sort") sort: String? = null,
    @Query("startDate") startDate: String? = null,
    @Query("endDate") endDate: String? = null,
  ): NoticeListResponse
}
