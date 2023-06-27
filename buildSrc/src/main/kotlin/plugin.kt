/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

@file:Suppress("NOTHING_TO_INLINE", "ObjectPropertyName")

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

inline fun PluginDependenciesSpec.eggeum(pluginId: String): PluginDependencySpec =
  id("eggeum.plugin.$pluginId")

inline fun PluginDependenciesSpec.android(pluginId: String): PluginDependencySpec =
  id("com.android.$pluginId")

val PluginDependenciesSpec.`kotlin-parcelize`: PluginDependencySpec
  inline get() = id("kotlin-parcelize")
