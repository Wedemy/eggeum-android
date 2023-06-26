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
  `kotlin-parcelize`
  alias(libs.plugins.android.hilt)
  `kotlin-kapt`
}

android {
  namespace = "us.wedemy.eggeum.setting"

  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementations(
    libs.android.material,
    libs.androidx.core,
    libs.androidx.constraintlayout,
    libs.kotlinx.coroutines.android,
    libs.bundles.androidx.lifecycle,
    libs.bundles.androidx.navigation,
    libs.coil,
    libs.timber,
    libs.android.hilt.runtime,
    projects.designResource,
    projects.common,
  )
  kapts(
    libs.android.hilt.compile,
  )
}
