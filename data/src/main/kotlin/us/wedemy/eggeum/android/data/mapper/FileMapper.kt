/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.mapper

import us.wedemy.eggeum.android.data.model.File
import us.wedemy.eggeum.android.data.model.ProfileImage
import us.wedemy.eggeum.android.data.model.file.FileResponse
import us.wedemy.eggeum.android.data.model.place.Image
import us.wedemy.eggeum.android.domain.model.ProfileImageEntity
import us.wedemy.eggeum.android.domain.model.FileEntity
import us.wedemy.eggeum.android.domain.model.file.FileResponseEntity
import us.wedemy.eggeum.android.domain.model.place.ImageEntity

public fun FileResponse.toEntity(): FileResponseEntity =
  FileResponseEntity(
    name = name,
    uploadFileId = uploadFileId,
    url = url,
  )

public fun File.toEntity(): FileEntity =
  FileEntity(
    uploadFileId = uploadFileId,
    url = url,
  )

public fun FileEntity.toModel(): File =
  File(
    uploadFileId = uploadFileId,
    url = url,
  )

public fun Image.toEntity(): ImageEntity =
  ImageEntity(
    files = files?.map { it.toEntity() },
  )

public fun ImageEntity.toModel(): Image =
  Image(
    files = files?.map { it.toModel() },
  )

internal fun ProfileImage.toEntity() =
  ProfileImageEntity(
    files = files.map { it.toEntity() },
  )

internal fun ProfileImageEntity.toModel() =
  ProfileImage(
    files = files.map { it.toModel() },
  )
