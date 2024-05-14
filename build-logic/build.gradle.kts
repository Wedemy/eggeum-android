/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION", "INLINE_FROM_HIGHER_PLATFORM")

plugins {
  `kotlin-dsl`
  kotlin("jvm") version libs.versions.kotlin.core.get()
  alias(libs.plugins.gradle.dependency.handler.extensions)
}

gradlePlugin {
  val pluginClasses = listOf(
    "AndroidApplicationPlugin" to "android-application",
    "AndroidLibraryPlugin" to "android-library",
    "AndroidHiltPlugin" to "android-hilt",
    "AndroidGmdPlugin" to "android-gmd",
    "AndroidFirebasePlugin" to "android-firebase",
    "AndroidxRoomPlugin" to "androidx-room",
    "JvmKotlinPlugin" to "jvm-kotlin",
    "KotlinExplicitApiPlugin" to "kotlin-explicit-api",
    "TestJUnitPlugin" to "test-junit",
    "TestKotestPlugin" to "test-kotest",
  )

  plugins {
    pluginClasses.forEach { pluginClass ->
      pluginRegister(pluginClass)
    }
  }
}

repositories {
  google()
  mavenCentral()
  gradlePluginPortal()
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
  jvmToolchain(17)
}

sourceSets {
  getByName("main").java.srcDir("src/main/kotlin")
}

dependencies {
  compileOnly(libs.gradle.android)
  compileOnly(libs.gradle.kotlin)
  compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

// Pair<ClassName, PluginName>
fun NamedDomainObjectContainer<PluginDeclaration>.pluginRegister(data: Pair<String, String>) {
  val (className, pluginName) = data
  register(pluginName) {
    implementationClass = className
    id = "eggeum.plugin.$pluginName"
  }
}
