/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import us.wedemy.eggeum.android.data.model.notice.NoticeResponse
import us.wedemy.eggeum.android.data.model.notice.NoticeListResponse

public interface NoticeService {

  @GET("notice/{noticeId}")
  public suspend fun getNotice(
    @Path("noticeId") noticeId: Int,
  ): Response<NoticeResponse>

  @GET("notice/all")
  public suspend fun getNoticeList(
    @Query("search") search: String? = null,
    @Query("page") page: Int? = null,
    @Query("size") size: Int? = null,
    @Query("sort") sort: String? = null,
    @Query("startDate") startDate: String? = null,
    @Query("endDate") endDate: String? = null,
  ): NoticeListResponse
}
