/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.util

import java.io.IOException

// LoginApi
public val LoginApiResponseIsNull: IOException = IOException("The Login API response is null.")
public val LoginApiResponseNotFound: IOException = IOException("Login API returned Not Found.")
public val LoginApiResponseUnknownError: IOException = IOException("Login API returned an unknown error.")

// EnumApi
public val EnumApiResponseIsNull: IOException = IOException("The Enum API response is null.")

// NoticeApi
public val NoticeApiResponseIsNull: IOException = IOException("The Notice API response is null.")

// UserApi
public val GetUserInfoApiResponseIsNull: IOException = IOException("GetUserInfo API response is null.")
public val CheckNicknameExistResponseIsNull: IOException = IOException("CheckNicknameExist API response is null.")
