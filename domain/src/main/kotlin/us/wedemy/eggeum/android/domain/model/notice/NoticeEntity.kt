/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.model.notice

public data class NoticeEntity(
  val id: Int,
  val title: String,
  val content: String,
  val viewCount: Int,
  val createdDate: String,
  val modifiedDate: String,
)
