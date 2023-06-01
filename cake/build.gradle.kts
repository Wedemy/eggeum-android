/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM", "UnstableApiUsage")

plugins {
  eggeum("android-library")
  eggeum("kotlin-explicit-api")
  eggeum("test-junit")
  alias(libs.plugins.test.roborazzi)
}

android {
  namespace = "us.wedemy.eggeum.cake"

  buildFeatures {
    viewBinding = true
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
      isReturnDefaultValues = true
      all { test ->
        test.systemProperty("robolectric.graphicsMode", "NATIVE")
      }
    }
  }
}

dependencies {
  testImplementations(
    libs.test.kotest.assertion,
    libs.test.robolectric,
    libs.bundles.test.roborazzi,
  )
}
