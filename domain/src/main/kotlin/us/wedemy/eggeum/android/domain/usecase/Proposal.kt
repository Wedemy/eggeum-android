
/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton
import us.wedemy.eggeum.android.domain.model.place.PlaceBody
import us.wedemy.eggeum.android.domain.model.place.ProposePlaceInfoCommand
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceBody
import us.wedemy.eggeum.android.domain.repository.PlaceRepository
import us.wedemy.eggeum.android.domain.util.GetPlaceBodyResponseIsNull
import us.wedemy.eggeum.android.domain.util.runSuspendCatching

@Singleton
public class ProposeCafeInfoUsecase @Inject constructor(
  private val repository: PlaceRepository,
) {
  public suspend fun execute(proposePlaceInfoCommand: ProposePlaceInfoCommand) {
    repository.upsertPlace(proposePlaceInfoCommand)
  }
}

@Singleton
public class GetPlaceBodyUsecase @Inject constructor(
  private val repository: PlaceRepository,
) {
  public suspend fun execute(placeId: Int): Result<PlaceBody> =
    runSuspendCatching {
      repository
        .getPlaceBody(
          placeId,
        ) ?: throw GetPlaceBodyResponseIsNull
    }
}
