/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.place

public data class ProposePlaceMenuCommand(
  val placeId: Int,
  val menu: Menu,
) {
  public companion object {
    public fun fixture(): ProposePlaceMenuCommand {
      return ProposePlaceMenuCommand(
        placeId = 0,
        menu = Menu.fixture(),
      )
    }
  }
}