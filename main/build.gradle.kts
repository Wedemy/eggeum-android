/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */
@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")


plugins {
  eggeum("android-library")
  alias(libs.plugins.androidx.navigation.safeargs)
  alias(libs.plugins.android.hilt)
  kotlin("kapt")
  `kotlin-parcelize`
}

android {
  namespace = "us.wedemy.eggeum.main"

  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  kapt(libs.android.hilt.compile)
  implementations(
    libs.android.material,
    libs.android.hilt.runtime,
    libs.androidx.core,
    libs.androidx.constraintlayout,
    libs.kotlinx.coroutines.android,
    libs.bundles.androidx.lifecycle,
    libs.bundles.androidx.navigation,
    libs.coil,
    libs.timber,
    projects.common,
    projects.designResource,
  )
}
