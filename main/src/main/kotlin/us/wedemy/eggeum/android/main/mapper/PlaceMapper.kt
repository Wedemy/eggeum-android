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
import us.wedemy.eggeum.android.main.model.CafeDetailModel
import us.wedemy.eggeum.android.main.model.ImageModel
import us.wedemy.eggeum.android.main.model.InfoModel
import us.wedemy.eggeum.android.main.model.MenuModel
import us.wedemy.eggeum.android.main.model.ProductModel

internal fun PlaceEntity.toUiModel() =
  CafeDetailModel(
    address1 = address1,
    address2 = address2,
    id = id,
    image = image.toUiModel(),
    info = info.toUilModel(),
    menu = menu.toUiModel(),
    name = name,
  )

internal fun ImageEntity.toUiModel() =
  ImageModel(
    files = files.map { it.toUiModel() }
  )

internal fun InfoEntity.toUilModel() =
  InfoModel(
    areaSize = areaSize,
    blogUri = blogUri,
    businessHours = businessHours,
    existsSmokingArea = existsSmokingArea,
    existsWifi = existsWifi,
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
  MenuModel(
    products = products.map { it.toUiModel() },
  )

internal fun ProductEntity.toUiModel() =
  ProductModel(
    name = name,
    price = price,
  )