/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.cake.snapshot.util

import java.io.File
import org.junit.rules.TestWatcher
import org.junit.runner.Description

const val BaseSnapshotPath = "src/test/snapshots"

class SnapshotPathGeneratorRule(private val domain: String) : TestWatcher() {
  init {
    File("$BaseSnapshotPath/$domain").mkdirs()
  }

  private var realtimeTestMethodName: String? = null

  override fun starting(description: Description) {
    realtimeTestMethodName = description.methodName
  }

  operator fun invoke(isGif: Boolean = false): File =
    File("$BaseSnapshotPath/$domain/$realtimeTestMethodName.${if (isGif) "gif" else "png"}")
}
