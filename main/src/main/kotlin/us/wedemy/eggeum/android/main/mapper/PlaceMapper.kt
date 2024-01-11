/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.main.mapper

import us.wedemy.eggeum.android.domain.model.place.ImageEntity
import us.wedemy.eggeum.android.domain.model.place.InfoEntity
import us.wedemy.eggeum.android.domain.model.place.MenuEntity
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.domain.model.place.ProductEntity
import us.wedemy.eggeum.android.common.model.CafeDetailModel
import us.wedemy.eggeum.android.common.model.ImageModel
import us.wedemy.eggeum.android.common.model.InfoModel
import us.wedemy.eggeum.android.common.model.MenuModel
import us.wedemy.eggeum.android.common.model.ProductModel

internal fun PlaceEntity.toUiModel() =
  CafeDetailModel(
    address1 = address1,
    address2 = address2,
    id = id,
    image = image?.toUiModel(),
    info = info?.toUilModel(),
    latitude = latitude,
    longitude = longitude,
    menu = menu?.toUiModel(),
    name = name,
  )

internal fun ImageEntity.toUiModel() =
  files?.let {
    ImageModel(files = it.map { it.toUiModel() })
  }

internal fun InfoEntity.toUilModel() =
  InfoModel(
    areaSize = areaSize,
    blogUri = blogUri,
    businessHours = businessHours,
    existsSmokingArea = existsSmokingArea,
    existsWifi = existsWifi,
    existsOutlet = existsOutlet,
    instagramUri = instagramUri,
    meetingRoomCount = meetingRoomCount,
    mobileCharging = mobileCharging,
    multiSeatCount = multiSeatCount,
    parking = parking,
    phone = phone,
    restRoom = restRoom,
    singleSeatCount = singleSeatCount,
    websiteUri = websiteUri,
  )

internal fun MenuEntity.toUiModel() =
  products?.let {
    MenuModel(products = it.map { it.toUiModel() })
  }

internal fun ProductEntity.toUiModel() =
  ProductModel(
    name = name,
    price = price,
  )
