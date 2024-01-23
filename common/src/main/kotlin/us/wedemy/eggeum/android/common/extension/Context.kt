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
import timber.log.Timber

val Context.layoutInflater
  get() = LayoutInflater.from(this) ?: error("Failed to create LayoutInflater")

fun Context.getAppVersionName(): String {
  return try {
    packageManager.getPackageInfo(packageName, 0).versionName
  } catch (exception: PackageManager.NameNotFoundException) {
    Timber.d(exception)
    "Unknown"
  }
}
