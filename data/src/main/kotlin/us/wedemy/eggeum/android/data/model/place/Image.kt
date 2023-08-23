/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.place

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import us.wedemy.eggeum.android.data.model.File

@JsonClass(generateAdapter = true)
public data class Image(
  @Json(name = "files")
  val files: List<File>,
)
