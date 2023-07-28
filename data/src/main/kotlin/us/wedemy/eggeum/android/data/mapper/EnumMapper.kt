/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.mapper

import us.wedemy.eggeum.android.data.model.enums.EnumListResponse
import us.wedemy.eggeum.android.domain.model.enums.AdditionalProperty
import us.wedemy.eggeum.android.domain.model.enums.EnumList

internal fun EnumListResponse.toDomain() =
  EnumList(
    additionalProp1 = additionalProp1.map { AdditionalProperty() },
    additionalProp2 = additionalProp2.map { AdditionalProperty() },
    additionalProp3 = additionalProp3.map { AdditionalProperty() },
  )
