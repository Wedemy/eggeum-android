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
import us.wedemy.eggeum.android.data.mapper.toDomain
import us.wedemy.eggeum.android.data.model.enums.EnumListResponse
import us.wedemy.eggeum.android.domain.model.enums.EnumList
import us.wedemy.eggeum.android.domain.repository.EnumRepository

@Singleton
public class EnumRepositoryProvider @Inject constructor(
  @Named("AuthHttpClient")
  private val client: HttpClient,
  moshi: Moshi,
) : EnumRepository {
  private val enumListAdapter = moshi.adapter<EnumListResponse>()
  override suspend fun getEnumList(): EnumList? {
    val responseText =
      client
        .get("/enums")
        .bodyAsText()
    val response = enumListAdapter.fromJson(responseText)
    return response?.toDomain()
  }
}
