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
  id("kotlin-parcelize")
}

android {
  namespace = "us.wedemy.eggeum.common"

  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementations(
    libs.androidx.core,
    libs.androidx.appcompat,
    libs.kotlinx.coroutine.android,
    libs.bundles.androidx.lifecycle,
    libs.bundles.androidx.navigation,
  )
}