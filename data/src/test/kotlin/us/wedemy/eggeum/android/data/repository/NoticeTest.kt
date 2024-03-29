/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.repository

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.engine.mock.respondOk
import io.ktor.http.fullPath
import org.intellij.lang.annotations.Language
import us.wedemy.eggeum.android.data.client.MoshiProvider
import us.wedemy.eggeum.android.domain.model.notice.NoticeEntity
import us.wedemy.eggeum.android.domain.model.notice.NoticeListEntity
import us.wedemy.eggeum.android.domain.repository.NoticeRepository

private object DummyResponse {
  @Language("json")
  const val Single =
    """
{
  "id": 0,
  "title": "공지사항 제목",
  "content": "공지사항 내용",
  "viewCount": 0
}
    """

  @Language("json")
  const val List =
    """
{
  "list": [
    {
      "id": 0,
      "title": "공지사항 제목",
      "content": "공지사항 내용",
      "viewCount": 0
    },
    {
      "id": 1,
      "title": "공지사항 제목2",
      "content": "공지사항 내용2",
      "viewCount": 1000
    }
  ],
  "totalElements": 2,
  "totalPages": 1
}  
    """
}

class NoticeTest : StringSpec() {
  private val testSingleNoticeId = 999

  private val mockEngine =
    MockEngine { request ->
      with(request.url.fullPath) {
        when {
          contains("notice/$testSingleNoticeId") -> respondOk(DummyResponse.Single)
          contains("notice/all") -> respondOk(DummyResponse.List)
          else -> respondBadRequest()
        }
      }
    }
  private val repository: NoticeRepository =
    NoticeRepositoryImpl(
      client = HttpClient(mockEngine),
      moshi = MoshiProvider.defaultMoshi(),
    )

  init {
    coroutineTestScope = true

    "single" {
      val actual = repository.getNotice(testSingleNoticeId)
      val expect =
        NoticeEntity(
          id = 0,
          content = "공지사항 내용",
          title = "공지사항 제목",
          viewCount = 0,
        )

      actual shouldBe expect
    }

    "list" {
      val actual =
        repository
          .getNoticeList(
            search = null,
            page = null,
            size = null,
            sort = null,
            startDate = null,
            endDate = null,
          )
      val expect =
        NoticeListEntity(
          list = listOf(
            NoticeEntity(
              id = 0,
              title = "공지사항 제목",
              content = "공지사항 내용",
              viewCount = 0,
            ),
            NoticeEntity(
              id = 1,
              title = "공지사항 제목2",
              content = "공지사항 내용2",
              viewCount = 1000,
            ),
          ),
          totalElements = 2,
          totalPages = 1,
        )

      actual shouldBe expect
    }
  }
}
