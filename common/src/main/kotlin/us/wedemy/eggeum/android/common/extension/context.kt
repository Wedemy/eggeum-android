/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.common.extension

import android.content.Context
import android.content.pm.PackageManager
import android.view.LayoutInflater

val Context.layoutInflater
  get() = LayoutInflater.from(this) ?: error("Failed to create LayoutInflater")

fun Context.getAppVersionName(): String {
  return try {
    packageManager.getPackageInfo(packageName, 0).versionName
  } catch (e: PackageManager.NameNotFoundException) {
    "Unknown"
  }
}