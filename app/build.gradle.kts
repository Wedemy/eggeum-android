/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

import java.util.Properties

plugins {
  eggeum("android-application")
  eggeum("android-hilt")
  // eggeum("android-firebase")
  alias(libs.plugins.google.secrets)
  alias(libs.plugins.google.services)
  alias(libs.plugins.firebase.crashlytics)
}

android {
  namespace = "us.wedemy.eggeum.android"

  signingConfigs {
    create("release") {
      val propertiesFile = rootProject.file("keystore.properties")
      val properties = Properties()
      properties.load(propertiesFile.inputStream())
      storeFile = file(properties["STORE_FILE"] as String)
      storePassword = properties["STORE_PASSWORD"] as String
      keyAlias = properties["KEY_ALIAS"] as String
      keyPassword = properties["KEY_PASSWORD"] as String
    }
  }

  buildTypes {
    getByName("debug") {
      isDebuggable = true
      applicationIdSuffix = ".dev"
      manifestPlaceholders += mapOf(
        "appName" to "@string/app_name_dev",
      )
    }

    getByName("release") {
      isDebuggable = false
      isMinifyEnabled = true
      isShrinkResources = true
      signingConfig = signingConfigs.getByName("release")
      manifestPlaceholders += mapOf(
        "appName" to "@string/app_name",
      )
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
      )
    }
  }

  buildFeatures {
    viewBinding = true
    buildConfig = true
  }
}

dependencies {
  implementations(
    libs.android.material,
    libs.androidx.splash,
    libs.androidx.startup,
    libs.androidx.activity,
    libs.androidx.appcompat,
    libs.androidx.constraintlayout,
    libs.bundles.androidx.lifecycle,
    libs.lottie,
    libs.insetter,
    libs.timber,
    libs.google.gms.play.services.auth,
    platform(libs.firebase.bom),
    libs.firebase.auth,
    libs.firebase.analytics,
    libs.firebase.crashlytics,

    projects.data,
    projects.domain,
    projects.login,
    projects.onboard,
    projects.main,
    projects.registerCafe,
    projects.designResource,
    projects.common,
    projects.updateCafe,
    projects.navigator,
  )
}

secrets {
  defaultPropertiesFileName = "secrets.properties"
}
