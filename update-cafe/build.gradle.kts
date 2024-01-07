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
  alias(libs.plugins.org.jetbrains.kotlin.android)
  `kotlin-parcelize`
}

android {
  namespace = "us.wedemy.eggeum.android.updatecafe"

  buildFeatures {
    viewBinding = true
    buildConfig = true
  }
}

dependencies {
  implementations(
    libs.kotlinx.coroutines.android,
    libs.android.material,
    libs.androidx.core,
    libs.androidx.constraintlayout,
    libs.bundles.androidx.lifecycle,
    libs.bundles.androidx.navigation,
    libs.timber,
    projects.common,
    projects.designResource,
    projects.navigator,
    projects.domain,
    projects.main,
  )
}
