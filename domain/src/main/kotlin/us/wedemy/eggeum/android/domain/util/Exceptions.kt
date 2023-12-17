/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.util

import java.io.IOException

// Login
public val LoginApiResponseIsNull: IOException = IOException("Login API response is null.")
public val LoginApiResponseNotFound: IOException = IOException("Login API returned Not Found.")
public val SignUpApiResponseIsNull: IOException = IOException("SignUp API response is null.")

// Enum
public val EnumApiResponseIsNull: IOException = IOException("The Enum API response is null.")

// Notice
public val NoticeApiResponseIsNull: IOException = IOException("The Notice API response is null.")

// User
public val GetUserInfoApiResponseIsNull: IOException = IOException("GetUserInfo API response is null.")
public val CheckNicknameExistResponseIsNull: IOException = IOException("CheckNicknameExist API response is null.")

// Place
public val PlaceApiResponseIsNull: IOException = IOException("Place API response is null.")

// Report
public val ReportApiResponseIsNull: IOException = IOException("Report API response is null.")

// File
public val FileApiResponseIsNull: IOException = IOException("File API response is null.")
