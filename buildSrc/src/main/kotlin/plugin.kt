/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

@Suppress("NOTHING_TO_INLINE")
inline fun PluginDependenciesSpec.eggeum(pluginId: String): PluginDependencySpec =
  id("eggeum.plugin.$pluginId")
