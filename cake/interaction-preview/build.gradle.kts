/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

plugins {
  eggeum("android-application")
}

android {
  namespace = "us.wedemy.eggeum.cake.preview"
}

dependencies {
  implementation(projects.cake)
}
