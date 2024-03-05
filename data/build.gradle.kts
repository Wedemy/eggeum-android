/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("INLINE_FROM_HIGHER_PLATFORM", "UnstableApiUsage")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  eggeum("android-library")
  eggeum("android-hilt")
  eggeum("kotlin-explicit-api")
  eggeum("test-kotest")
  eggeum("androidx-room")
  alias(libs.plugins.google.secrets)
  alias(libs.plugins.kotlinx.serialization)
  alias(libs.plugins.moshix.ir)
}

android {
  namespace = "us.wedemy.eggeum.android.data"

  buildFeatures {
    buildConfig = true
  }
}

dependencies {
  implementations(
    projects.domain,
    projects.common,

    libs.androidx.datastore.preferences,
    libs.timber,
    libs.moshi.core,
    libs.moshi.kotlin,
    libs.kotlinx.serialization.json,
    libs.androidx.paging.runtime,
    libs.bundles.ktor.client,
    libs.bundles.retrofit,
  )
  testImplementation(libs.test.ktor.client.mock)
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = freeCompilerArgs + listOf("-opt-in=kotlin.ExperimentalStdlibApi")
  }
}

secrets {
  defaultPropertiesFileName = "secrets.properties"
}
