/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.place

import us.wedemy.eggeum.android.domain.model.File

public data class Image(
  val files: List<File>,
) {
  public companion object {
    public fun fixture(): Image {
      return Image(
        files = listOf(File.fixture()),
      )
    }
  }
}
