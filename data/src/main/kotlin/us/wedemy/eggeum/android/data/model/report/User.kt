/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.report

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import us.wedemy.eggeum.android.data.model.ProfileImage

@JsonClass(generateAdapter = true)
public data class User(
  @Json(name = "agreeMarketing")
  val agreeMarketing: Boolean,

  @Json(name = "createdBy")
  val createdBy: Int,

  @Json(name = "createdDate")
  val createdDate: String,

  @Json(name = "email")
  val email: String,

  @Json(name = "id")
  val id: Int,

  @Json(name = "modifiedBy")
  val modifiedBy: Int,

  @Json(name = "modifiedDate")
  val modifiedDate: String,

  @Json(name = "name")
  val name: String,

  @Json(name = "nickname")
  val nickname: String,

  @Json(name = "password")
  val password: String,

  @Json(name = "phoneNumber")
  val phoneNumber: String,

  @Json(name = "profileImage")
  val profileImage: ProfileImage,

  @Json(name = "roles")
  val roles: List<String>,

  @Json(name = "snsId")
  val snsId: String,

  @Json(name = "status")
  val status: String,
)
