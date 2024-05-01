/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.repository

import javax.inject.Inject
import us.wedemy.eggeum.android.data.datasource.file.FileDataSource
import us.wedemy.eggeum.android.data.mapper.toEntity
import us.wedemy.eggeum.android.domain.model.file.FileResponseEntity
import us.wedemy.eggeum.android.domain.repository.FileRepository

internal class FileRepositoryImpl @Inject constructor(
  private val fileDataSource: FileDataSource,
) : FileRepository {
  override suspend fun uploadImageFile(uri: String): FileResponseEntity {
    return fileDataSource.uploadImageFile(uri).toEntity()
  }
}
