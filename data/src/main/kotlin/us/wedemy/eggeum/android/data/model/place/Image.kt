/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.place

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import us.wedemy.eggeum.android.data.model.File

@Serializable
public data class Image(
  @SerialName("files")
  val files: List<File>?,
)
