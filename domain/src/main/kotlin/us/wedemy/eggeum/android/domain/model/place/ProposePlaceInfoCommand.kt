/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.place

public data class ProposePlaceInfoCommand(
  val placeId: Int,
  val info: Info,
) {
  public companion object {
    public fun fixture(): ProposePlaceInfoCommand {
      return ProposePlaceInfoCommand(
        placeId = 0,
        info = Info.fixture(),
      )
    }
  }
}