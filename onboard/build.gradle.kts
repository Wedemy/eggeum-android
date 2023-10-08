/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

plugins {
  eggeum("android-library")
  eggeum("android-hilt")
  alias(libs.plugins.androidx.navigation.safeargs)
  `kotlin-parcelize`
}

android {
  namespace = "us.wedemy.eggeum.android.onboard"

  buildFeatures {
    buildConfig = true
    viewBinding = true
  }
}

dependencies {
  implementations(
    libs.kotlinx.coroutines.android,
    libs.android.material,
    libs.androidx.core,
    libs.androidx.constraintlayout,
    libs.timber,
    libs.bundles.androidx.lifecycle,
    libs.bundles.androidx.navigation,
    projects.common,
    projects.designResource,
    projects.domain,
    projects.navigator,
  )
}
