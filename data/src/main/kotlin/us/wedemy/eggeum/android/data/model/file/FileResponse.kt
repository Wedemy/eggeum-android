/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.model.file

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class FileResponse(
  @SerialName("name")
  val name: String?,

  @SerialName("uploadFileId")
  val uploadFileId: Int?,

  @SerialName("url")
  val url: String?,
)
