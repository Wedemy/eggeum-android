/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM", "UnstableApiUsage")

plugins {
  eggeum("android-library")
  kotlin("kapt")
  alias(libs.plugins.android.hilt)
}

android {
  namespace = "us.wedemy.eggeum.android.data"
}

dependencies {
  kapt(libs.android.hilt.compile)
  implementations(
    libs.android.hilt.runtime,
    libs.timber,
    libs.bundles.jackson,
    libs.bundles.ktor.client,
  )
}
