/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.usecase

import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.domain.model.enums.EnumListEntity
import us.wedemy.eggeum.android.domain.repository.EnumRepository
import us.wedemy.eggeum.android.domain.util.runSuspendCatching

public val EnumApiResponseIsNull: IOException = IOException("The Enum API response is null.")

@Singleton
public class GetEnumListUseCase @Inject constructor(
  private val repository: EnumRepository,
) {
  public suspend operator fun invoke(): Result<EnumListEntity> =
    runSuspendCatching {
      repository.getEnumList() ?: throw EnumApiResponseIsNull
    }
}
