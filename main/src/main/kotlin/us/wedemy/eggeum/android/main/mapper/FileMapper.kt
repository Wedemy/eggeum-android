/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.mapper

import us.wedemy.eggeum.android.domain.model.FileEntity
import us.wedemy.eggeum.android.main.model.FileModel

internal fun FileEntity.toUiModel() =
  FileModel(
    uploadFileId = uploadFileId,
    url = url,
  )

internal fun FileModel.toEntity() =
  FileEntity(
    uploadFileId = uploadFileId,
    url = url,
  )