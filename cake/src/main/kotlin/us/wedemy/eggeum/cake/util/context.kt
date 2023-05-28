/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.cake.util

import android.content.Context
import android.view.LayoutInflater

public val Context.layoutInflater: LayoutInflater
  get() = LayoutInflater.from(this)
