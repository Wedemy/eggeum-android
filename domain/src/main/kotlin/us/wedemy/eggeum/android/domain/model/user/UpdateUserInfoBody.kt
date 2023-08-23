/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.user

import us.wedemy.eggeum.android.domain.model.ProfileImage

public data class UpdateUserInfoBody(
  public val nickname: String,
  public val profileImage: ProfileImage,
)
