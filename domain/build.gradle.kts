/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

plugins {
  eggeum("jvm-kotlin")
  eggeum("kotlin-explicit-api")
  eggeum("test-kotest")
}

dependencies {
  implementation(libs.javax.inject)
}
