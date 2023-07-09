/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.domain.model.notice

public data class NoticeBody(
  public val id: Int,
  public val title: String,
  public val content: String,
  public val viewCount: Int,
)
