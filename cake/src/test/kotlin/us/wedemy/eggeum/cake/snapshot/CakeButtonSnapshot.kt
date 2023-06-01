/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.cake.snapshot

import android.widget.TextView
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import us.wedemy.eggeum.cake.CakeButton
import us.wedemy.eggeum.cake.R
import us.wedemy.eggeum.cake.snapshot.util.SnapshotPathGeneratorRule
import us.wedemy.eggeum.cake.util.getActivityViaRobolectric
import us.wedemy.eggeum.cake.util.px
import us.wedemy.eggeum.cake.util.setHorizontalPadding

@RunWith(RobolectricTestRunner::class)
class CakeButtonSnapshot {
  @get:Rule
  val snapshotPath = SnapshotPathGeneratorRule("CakeButton")

  @Test
  fun on_status() {
    val cakeButton = CakeButton(getActivityViaRobolectric).apply {
      text = "Hello, World!"
      enable = true
    }
    cakeButton.findViewById<TextView>(R.id.cake_button_text).apply {
      setHorizontalPadding(100.px)
    }
    cakeButton.captureRoboImage(snapshotPath())
  }

  @Test
  fun off_status() {
    val cakeButton = CakeButton(getActivityViaRobolectric).apply {
      text = "Hello, World!"
      enable = false
    }
    cakeButton.findViewById<TextView>(R.id.cake_button_text).apply {
      setHorizontalPadding(100.px)
    }
    cakeButton.captureRoboImage(snapshotPath())
  }
}
