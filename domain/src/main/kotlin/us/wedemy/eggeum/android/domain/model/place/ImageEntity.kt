/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.place

import us.wedemy.eggeum.android.domain.model.FileEntity

public data class ImageEntity(
  val files: List<FileEntity>?,
) {
  public companion object {
    public fun of(
      files: List<FileEntity>? = null,
    ): ImageEntity {
      return ImageEntity(
        files = files,
      )
    }
  }
}
