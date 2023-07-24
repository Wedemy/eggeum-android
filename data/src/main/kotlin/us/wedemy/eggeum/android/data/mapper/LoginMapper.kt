/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.mapper

import us.wedemy.eggeum.android.data.model.login.LoginBodyResponse
import us.wedemy.eggeum.android.data.model.login.SignUpBodyResponse
import us.wedemy.eggeum.android.domain.model.login.LoginBody
import us.wedemy.eggeum.android.domain.model.login.SignUpBody

internal fun LoginBodyResponse.toDomain() =
  LoginBody(
    accessToken = accessToken,
    expiresIn = expiresIn,
    refreshToken = refreshToken,
    refreshExpiresIn = refreshExpiresIn,
    userRoles = userRoles,
  )

internal fun SignUpBodyResponse.toDomain() =
  SignUpBody(
    accessToken = accessToken,
    expiresIn = expiresIn,
    refreshToken = refreshToken,
    refreshExpiresIn = refreshExpiresIn,
    userRoles = userRoles,
  )
