/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.domain.repository

import us.wedemy.eggeum.android.domain.model.enums.EnumListEntity

/** Enum 조회 API */
public interface EnumRepository {
  /**
   * 전체 Enum 목록 조회
   */
  public suspend fun getEnumList(): EnumListEntity?
}
