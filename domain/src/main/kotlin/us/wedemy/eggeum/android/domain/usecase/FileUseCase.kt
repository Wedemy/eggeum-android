/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.domain.model.file.FileResponseEntity
import us.wedemy.eggeum.android.domain.repository.FileRepository
import us.wedemy.eggeum.android.domain.util.runSuspendCatching

@Singleton
public class UploadImageFileUseCase @Inject constructor(
  private val repository: FileRepository,
) {
  public suspend operator fun invoke(uri: String): Result<FileResponseEntity> =
    runSuspendCatching {
      repository.uploadImageFile(uri)
    }
}
