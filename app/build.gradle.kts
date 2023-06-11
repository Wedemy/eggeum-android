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
}

dependencies {
  implementations(
    libs.androidx.appcompat,
    projects.designResource,
    projects.onboard,
  )
}
