/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import us.wedemy.eggeum.android.common.model.FileModel

@Parcelize
data class ProfileImageModel(
  val files: List<FileModel>,
) : Parcelable
