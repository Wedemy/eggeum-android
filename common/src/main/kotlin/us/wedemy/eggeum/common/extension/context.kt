/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.common.extension

import android.content.Context
import android.view.LayoutInflater

val Context.layoutInflater
  get() = LayoutInflater.from(this) ?: error("Failed to create LayoutInflater")
