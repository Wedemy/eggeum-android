/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.file

import us.wedemy.eggeum.android.data.model.file.FileResponse

public interface FileDataSource {
  public suspend fun uploadImageFile(uri: String): FileResponse
}
