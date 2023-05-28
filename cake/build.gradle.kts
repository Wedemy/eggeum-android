/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION", "INLINE_FROM_HIGHER_PLATFORM")

plugins {
  eggeum("android-library")
  eggeum("test-junit")
  alias(libs.plugins.test.roborazzi)
}

android {
  namespace = "us.wedemy.eggeum.cake"
}

dependencies {
  implementation(libs.androidx.appcompat)
  testImplementations(
    libs.test.robolectric,
    libs.bundles.test.roborazzi,
  )
}
