/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.cake.uitest

import io.kotest.matchers.shouldBe
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import us.wedemy.eggeum.cake.CakeButton
import us.wedemy.eggeum.cake.util.getActivityViaRobolectric

@RunWith(RobolectricTestRunner::class)
class CakeButtonTest {
  @Test
  fun `onClick only works when enabled`() {
    var invokeCount = 0

    val cakeButton = CakeButton(getActivityViaRobolectric).apply {
      enable = true
      setOnClickListener { invokeCount++ }
    }
    cakeButton.performClick()

    cakeButton.enable = false
    cakeButton.performClick()

    invokeCount shouldBe 1
  }
}
