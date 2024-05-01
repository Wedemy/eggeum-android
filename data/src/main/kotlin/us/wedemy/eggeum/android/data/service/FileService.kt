/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.service

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import us.wedemy.eggeum.android.data.model.file.FileResponse

internal interface FileService {

  @Multipart
  @POST("files/images")
  suspend fun uploadImageFile(
    @Part file: MultipartBody.Part,
  ): FileResponse
}
