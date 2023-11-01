/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.datasource.file

import android.content.Context
import android.net.Uri
import javax.inject.Inject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import us.wedemy.eggeum.android.data.model.file.FileResponse
import us.wedemy.eggeum.android.data.service.FileService
import us.wedemy.eggeum.android.data.util.FileManager
import us.wedemy.eggeum.android.data.util.safeRequest

public class FileDataSourceImpl @Inject constructor(
  private val context: Context,
  private val service: FileService,
  private val fileManager: FileManager,
) : FileDataSource {
  override suspend fun uploadImageFile(uri: String): FileResponse? {
    val imageFile = fileManager.getFileForUploadImageFormat(context, Uri.parse(uri))!!
    val imageRequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())

    return safeRequest {
      service.uploadImageFile(
        MultipartBody.Part.createFormData(
          name = "file",
          filename = imageFile.name,
          body = imageRequestBody,
        ),
      )
    }
  }
}
