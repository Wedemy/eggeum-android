/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

plugins {
  eggeum("android-application")
}

android {
  namespace = "us.wedemy.eggeum.android"

  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementations(
    libs.androidx.splash,
    libs.androidx.activity,
    libs.androidx.appcompat, // needed for Lottie
    libs.androidx.constraintlayout,
    libs.lottie,
    libs.insetter,
    projects.designResource,
    projects.common,
    projects.onboard,
    projects.registerCafe,
  )
}
