/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.enums

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Ktor 로 uri -> mulipart 변환 및 Authenticator 구현의 어려움을 겪어 Retrofit 으로 migration
// 추후 방법을 알아내어 Ktor 로 이를 구현할 예정

@JsonClass(generateAdapter = true)
internal data class EnumListResponse(
  @Json(name = "additionalProp1")
  val additionalProp1: List<AdditionalProperty>,

  @Json(name = "additionalProp2")
  val additionalProp2: List<AdditionalProperty>,

  @Json(name = "additionalProp3")
  val additionalProp3: List<AdditionalProperty>,
)

@JsonClass(generateAdapter = true)
public class AdditionalProperty
