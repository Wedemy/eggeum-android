/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.usecase

import androidx.paging.PagingData
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import us.wedemy.eggeum.android.domain.model.place.PlaceBody
import us.wedemy.eggeum.android.domain.repository.PlaceRepository
import us.wedemy.eggeum.android.domain.util.GetPlaceBodyApiResponseIsNull
import us.wedemy.eggeum.android.domain.util.runSuspendCatching

@Singleton
public class GetPlaceListUseCase @Inject constructor(
  private val repository: PlaceRepository,
) {
  public suspend fun execute(): Flow<PagingData<PlaceBody>> {
    return repository.getPlaceList()
  }
}

@Singleton
public class GetPlaceBodyUseCase @Inject constructor(
  private val repository: PlaceRepository,
) {
  public suspend fun execute(placeId: Int): Result<PlaceBody> =
    runSuspendCatching {
      repository.getPlaceBody(placeId) ?: throw GetPlaceBodyApiResponseIsNull
    }
}
