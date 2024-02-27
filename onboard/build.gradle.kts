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

  defaultConfig {
    buildConfigField(
      "String",
      "PRIVACY_POLICY_WEB_VIEW_URL",
      properties["PRIVACY_POLICY_WEB_VIEW_URL"] as String
    )

    buildConfigField(
      "String",
      "PRIVACY_THIRD_WEB_VIEW_URL",
      properties["PRIVACY_THIRD_WEB_VIEW_URL"] as String
    )

    buildConfigField(
      "String",
      "SERVICE_TERMS_WEB_VIEW_URL",
      properties["SERVICE_TERMS_WEB_VIEW_URL"] as String
    )
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
    libs.insetter,
    projects.common,
    projects.designResource,
    projects.domain,
    projects.navigator,
  )
}
