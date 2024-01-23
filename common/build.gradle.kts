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
}

android {
  namespace = "us.wedemy.eggeum.android.common"

  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementations(
    libs.androidx.core,
    libs.androidx.activity,
    libs.kotlinx.coroutines.android,
    libs.lottie,
    libs.insetter,
    libs.timber,
    libs.bundles.androidx.lifecycle,
    libs.bundles.androidx.navigation,
    projects.designResource,
  )
}
