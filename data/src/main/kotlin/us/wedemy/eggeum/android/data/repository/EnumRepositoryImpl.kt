/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import us.wedemy.eggeum.android.data.mapper.toEntity
import us.wedemy.eggeum.android.data.model.enums.EnumListResponse
import us.wedemy.eggeum.android.domain.model.enums.EnumListEntity
import us.wedemy.eggeum.android.domain.repository.EnumRepository

// Ktor 로 uri -> mulipart 변환 및 Authenticator 구현의 어려움을 겪어 Retrofit 으로 migration
// 추후 방법을 알아내어 Ktor 로 이를 구현할 예정
@Singleton
public class EnumRepositoryImpl @Inject constructor(
  @Named("KtorHttpClient")
  private val client: HttpClient,
  moshi: Moshi,
) : EnumRepository {
  private val enumListAdapter = moshi.adapter<EnumListResponse>()
  override suspend fun getEnumList(): EnumListEntity? {
    val responseText =
      client
        .get("/enums")
        .bodyAsText()
    val response = enumListAdapter.fromJson(responseText)
    return response?.toEntity()
  }
}
