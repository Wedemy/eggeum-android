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
  alias(libs.plugins.google.secrets)
  alias(libs.plugins.androidx.navigation.safeargs)
}

android {
  namespace = "us.wedemy.eggeum.main"

  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementations(
    libs.kotlinx.coroutines.android,
    libs.android.material,
    libs.android.play.services.location,
    libs.androidx.core,
    libs.androidx.constraintlayout,
    libs.bundles.androidx.lifecycle,
    libs.bundles.androidx.navigation,
    libs.coil,
    libs.timber,
    libs.naver.map,
    projects.common,
    projects.designResource,
  )
}

secrets {
  defaultPropertiesFileName = "secrets.properties"
}
