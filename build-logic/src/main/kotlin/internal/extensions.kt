/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("NOTHING_TO_INLINE")

package internal

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.NamedDomainObjectContainerScope
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
  get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.applyPlugins(vararg plugins: String) {
  plugins.forEach(pluginManager::apply)
}

internal inline operator fun <T : Any, C : NamedDomainObjectContainer<T>> C.invoke(
  configuration: Action<NamedDomainObjectContainerScope<T>>,
): C =
  apply {
    configuration.execute(NamedDomainObjectContainerScope.of(this))
  }

internal inline fun DependencyHandler.setupJunit(core: Any, engine: Any) {
  add("testImplementation", core)
  add("testRuntimeOnly", engine)
}
