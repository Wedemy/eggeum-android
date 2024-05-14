/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("UnstableApiUsage", "unused")

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import internal.ApplicationConstants
import internal.applyPlugins
import internal.configureAndroid
import internal.configureGmd
import internal.libs
import internal.androidExtensions
import internal.detektPlugins
import internal.implementation
import internal.isAndroidProject
import internal.ksp
import internal.setupJunit
import internal.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.AbstractTestTask
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestResult
import org.gradle.api.tasks.testing.TestDescriptor
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.gradle.kotlin.dsl.KotlinClosure2
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private const val EXPLICIT_API = "-Xexplicit-api=strict"

internal abstract class BuildLogicPlugin(private val block: Project.() -> Unit) : Plugin<Project> {
  final override fun apply(project: Project) {
    with(project, block = block)
  }
}

internal class AndroidApplicationPlugin : BuildLogicPlugin({
  applyPlugins(Plugins.AndroidApplication, Plugins.KotlinAndroid)

  extensions.configure<BaseAppModuleExtension> {
    configureAndroid(this)

    defaultConfig {
      targetSdk = ApplicationConstants.TargetSdk
      versionCode = ApplicationConstants.VersionCode
      versionName = ApplicationConstants.VersionName
    }
  }
})

internal class AndroidLibraryPlugin : BuildLogicPlugin({
  applyPlugins(Plugins.AndroidLibrary, Plugins.KotlinAndroid)

  extensions.configure<LibraryExtension> {
    configureAndroid(this)

    defaultConfig.apply {
      targetSdk = ApplicationConstants.TargetSdk
    }
  }
})

internal class AndroidHiltPlugin : BuildLogicPlugin({
  applyPlugins(Plugins.hilt, Plugins.Ksp)
  dependencies {
    implementation(libs.android.hilt.runtime)
    ksp(libs.android.hilt.compile)
  }
})

internal class AndroidxRoomPlugin : BuildLogicPlugin({
  applyPlugins(Plugins.Ksp)

  dependencies {
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compile)
  }
})

internal class AndroidGmdPlugin : BuildLogicPlugin({
  configureGmd(androidExtensions)
})

internal class AndroidFirebasePlugin : BuildLogicPlugin({
  applyPlugins(Plugins.GoogleServices, Plugins.FirebaseCrashlytics)

  dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.google.gms.play.services.auth)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.auth)
  }
})

internal class JvmKotlinPlugin : BuildLogicPlugin({
  applyPlugins(Plugins.JavaLibrary, Plugins.KotlinJvm)

  extensions.configure<JavaPluginExtension> {
    sourceCompatibility = ApplicationConstants.JavaVersion
    targetCompatibility = ApplicationConstants.JavaVersion
  }

  extensions.configure<KotlinProjectExtension> {
    jvmToolchain(ApplicationConstants.JavaVersionAsInt)
  }

  extensions.configure<SourceSetContainer> {
    getByName("main").java.srcDir("src/main/kotlin")
    getByName("test").java.srcDir("src/test/kotlin")
  }
  dependencies {
    detektPlugins(libs.detekt.plugin.formatting)
  }
})

internal class KotlinExplicitApiPlugin : BuildLogicPlugin({
  tasks
    .matching { task ->
      task is KotlinCompile &&
        !task.name.contains("test", ignoreCase = true)
    }
    .configureEach {
      if (!project.hasProperty("kotlin.optOutExplicitApi")) {
        val kotlinCompile = this as KotlinCompile
        if (EXPLICIT_API !in kotlinCompile.kotlinOptions.freeCompilerArgs) {
          kotlinCompile.kotlinOptions.freeCompilerArgs += EXPLICIT_API
        }
      }
    }
})

internal class TestJUnitPlugin : BuildLogicPlugin({
  useTestPlatformForTarget()
  dependencies {
    setupJunit(
      core = libs.test.junit.core,
      engine = libs.test.junit.engine,
    )
  }
})

internal class TestKotestPlugin : BuildLogicPlugin({
  useTestPlatformForTarget()
  dependencies {
    testImplementation(libs.test.kotest.framework)
  }
})

// ref: https://kotest.io/docs/quickstart#test-framework
private fun Project.useTestPlatformForTarget() {
  fun AbstractTestTask.setTestConfiguration() {
    // https://stackoverflow.com/a/36178581/14299073
    outputs.upToDateWhen { false }
    testLogging {
      events = setOf(
        TestLogEvent.PASSED,
        TestLogEvent.SKIPPED,
        TestLogEvent.FAILED,
      )
    }
    afterSuite(
      KotlinClosure2<TestDescriptor, TestResult, Unit>({ desc, result ->
        if (desc.parent == null) { // will match the outermost suite
          val output = "Results: ${result.resultType} " +
            "(${result.testCount} tests, " +
            "${result.successfulTestCount} passed, " +
            "${result.failedTestCount} failed, " +
            "${result.skippedTestCount} skipped)"
          println(output)
        }
      })
    )
  }

  if (isAndroidProject) {
    androidExtensions.testOptions {
      unitTests.all { test ->
        test.useJUnitPlatform()

        if (!test.name.contains("debug", ignoreCase = true)) {
          test.enabled = false
        }
      }
    }
    tasks.withType<Test>().configureEach {
      setTestConfiguration()
    }
  } else {
    tasks.withType<Test>().configureEach {
      useJUnitPlatform()
      setTestConfiguration()
    }
  }
}
