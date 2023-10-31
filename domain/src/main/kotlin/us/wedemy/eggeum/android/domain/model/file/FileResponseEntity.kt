/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.file

public data class FileResponseEntity(
  val name: String,
  val uploadFileId: Int,
  val url: String,
)
