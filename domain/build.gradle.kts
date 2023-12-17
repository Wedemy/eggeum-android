/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */
@file:Suppress("UnstableApiUsage", "INLINE_FROM_HIGHER_PLATFORM")

plugins {
  eggeum("jvm-kotlin")
  eggeum("kotlin-explicit-api")
  eggeum("test-kotest")
}

dependencies {
  implementations(
    libs.javax.inject,
    libs.kotlinx.coroutines.core,
    libs.androidx.paging.common,
  )
}
