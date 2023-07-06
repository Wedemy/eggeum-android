/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

plugins {
  eggeum("android-application")
  id(libs.plugins.google.gms.get().pluginId)
  alias(libs.plugins.android.hilt)
  kotlin("kapt")
}

android {
  namespace = "us.wedemy.eggeum.android"

  buildFeatures {
    viewBinding = true
    buildConfig = true
  }
}

dependencies {
  kapt(libs.android.hilt.compile)
  implementations(
    platform(libs.firebase.bom),
    libs.android.material,
    libs.android.hilt.runtime,
    libs.androidx.splash,
    libs.androidx.activity,
    libs.androidx.appcompat,
    libs.androidx.constraintlayout,
    libs.bundles.androidx.lifecycle,
    libs.firebase.auth,
    libs.google.gms.play.services.auth,
    libs.lottie,
    libs.insetter,
    libs.timber,
    projects.data,
    projects.designResource,
    projects.common,
    projects.onboard,
    projects.registerCafe,
    projects.setting,
  )
}
