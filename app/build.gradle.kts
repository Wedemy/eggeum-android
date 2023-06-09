/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

plugins {
  eggeum("android-application")
  eggeum("android-hilt")
  id(libs.plugins.google.gms.get().pluginId)
  alias(libs.plugins.google.secrets)
}

android {
  namespace = "us.wedemy.eggeum.android"

  buildFeatures {
    viewBinding = true
    buildConfig = true
  }
}

dependencies {
  implementations(
    platform(libs.firebase.bom),
    libs.android.material,
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
    libs.naver.map,
    projects.data,
    projects.domain,
    projects.main,
    projects.onboard,
    projects.registerCafe,
    projects.setting,
    projects.designResource,
    projects.common,
  )
}

secrets {
  defaultPropertiesFileName = "secrets.properties"
}
