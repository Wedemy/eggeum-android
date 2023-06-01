/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.cake.utiltest

import android.util.TypedValue
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import us.wedemy.eggeum.cake.util.getActivityViaRobolectric
import us.wedemy.eggeum.cake.util.px

@RunWith(RobolectricTestRunner::class)
class UnitTest {
  @Test
  fun `dip to pixel`() {
    val target = 10f

    val expect = target.px
    val actual = TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP,
      target,
      getActivityViaRobolectric.resources.displayMetrics,
    )

    expect shouldBe actual
  }
}
