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
  `kotlin-parcelize`
}

android {
  namespace = "us.wedemy.eggeum.android.main"

  buildFeatures {
    viewBinding = true
    buildConfig = true
  }

  defaultConfig {
    buildConfigField(
      "String",
      "PRIVACY_POLICY_WEB_VIEW_URL",
      properties["PRIVACY_POLICY_WEB_VIEW_URL"] as String
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
    projects.common,
    projects.designResource,
    projects.domain,
    projects.navigator,

    libs.kotlinx.coroutines.android,
    libs.kotlinx.datetime,
    libs.android.material,
    libs.android.play.services.location,
    libs.androidx.core,
    libs.androidx.constraintlayout,
    libs.androidx.paging.runtime,
    libs.bundles.androidx.lifecycle,
    libs.bundles.androidx.navigation,
    libs.coil,
    libs.timber,
    libs.naver.map,
    libs.photo.view,
    libs.insetter,
  )
}

secrets {
  defaultPropertiesFileName = "secrets.properties"
}
