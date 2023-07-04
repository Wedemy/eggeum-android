/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("DSL_SCOPE_VIOLATION")

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
  alias(libs.plugins.kotlin.detekt)
  alias(libs.plugins.kotlin.ktlint)
  alias(libs.plugins.gradle.dependency.handler.extensions)
  alias(libs.plugins.google.gms) apply false
  alias(libs.plugins.gradle.android.application) apply false
  alias(libs.plugins.gradle.android.library) apply false
  alias(libs.plugins.android.hilt) apply false
}

buildscript {
  repositories {
    google()
    mavenCentral()
  }

  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.core.get()}")
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }

  apply {
    plugin(rootProject.libs.plugins.kotlin.detekt.get().pluginId)
    plugin(rootProject.libs.plugins.kotlin.ktlint.get().pluginId)
    plugin(rootProject.libs.plugins.gradle.dependency.handler.extensions.get().pluginId)
  }

  afterEvaluate {
    extensions.configure<DetektExtension> {
      parallel = true
      buildUponDefaultConfig = true
      toolVersion = libs.versions.kotlin.detekt.get()
      config.setFrom(files("$rootDir/detekt-config.yml"))
    }

    extensions.configure<KtlintExtension> {
      version.set(rootProject.libs.versions.kotlin.ktlint.source.get())
      android.set(true)
      verbose.set(true)
    }

    tasks.withType<KotlinCompile> {
      kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
          "-opt-in=kotlin.OptIn",
          "-opt-in=kotlin.RequiresOptIn",
        )
      }
    }
  }
}

tasks.register("cleanAll", type = Delete::class) {
  allprojects.map(Project::getBuildDir).forEach(::delete)
}
