/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

plugins {
  eggeum("android-library")
}

android {
  namespace = "us.wedemy.eggeum.design"
}

dependencies {
  implementation(libs.android.material)
}
