/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.mapper

import us.wedemy.eggeum.android.data.model.login.LoginRequest
import us.wedemy.eggeum.android.data.model.login.LoginResponse
import us.wedemy.eggeum.android.data.model.login.SignUpRequest
import us.wedemy.eggeum.android.data.model.login.SignUpResponse
import us.wedemy.eggeum.android.domain.model.login.LoginRequestEntity
import us.wedemy.eggeum.android.domain.model.login.LoginResponseEntity
import us.wedemy.eggeum.android.domain.model.login.SignUpRequestEntity
import us.wedemy.eggeum.android.domain.model.login.SignUpResponseEntity

internal fun LoginRequestEntity.toModel() =
  LoginRequest(idToken = idToken)

internal fun LoginResponse.toEntity() =
  LoginResponseEntity(
    accessToken = accessToken,
    expiresIn = expiresIn,
    refreshToken = refreshToken,
    refreshExpiresIn = refreshExpiresIn,
    userRoles = userRoles,
  )

internal fun SignUpResponse.toEntity() =
  SignUpResponseEntity(
    accessToken = accessToken,
    expiresIn = expiresIn,
    refreshToken = refreshToken,
    refreshExpiresIn = refreshExpiresIn,
    userRoles = userRoles,
  )

internal fun SignUpRequestEntity.toModel() =
  SignUpRequest(
    agreemMarketing = agreemMarketing,
    idToken = idToken,
    nickname = nickname,
  )
