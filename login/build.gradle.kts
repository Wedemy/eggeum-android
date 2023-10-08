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
  id(libs.plugins.google.gms.get().pluginId)
  alias(libs.plugins.google.secrets)
}

android {
  namespace = "us.wedemy.eggeum.android.login"

  buildFeatures {
    viewBinding = true
    buildConfig = true
  }
}

dependencies {
  implementations(
    platform(libs.firebase.bom),
    libs.android.material,
    libs.androidx.activity,
    libs.androidx.appcompat,
    libs.androidx.constraintlayout,
    libs.bundles.androidx.lifecycle,
    libs.firebase.auth,
    libs.google.gms.play.services.auth,
    libs.insetter,
    libs.timber,
    projects.data,
    projects.domain,
    projects.designResource,
    projects.common,
    projects.navigator,
  )
}

secrets {
  defaultPropertiesFileName = "secrets.properties"
}
