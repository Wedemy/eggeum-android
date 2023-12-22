/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.mapper

import us.wedemy.eggeum.android.data.model.place.Info
import us.wedemy.eggeum.android.data.model.place.Menu
import us.wedemy.eggeum.android.data.model.place.PlaceListResponse
import us.wedemy.eggeum.android.data.model.place.PlaceResponse
import us.wedemy.eggeum.android.data.model.place.Product
import us.wedemy.eggeum.android.data.model.place.UpsertPlaceRequest
import us.wedemy.eggeum.android.domain.model.place.InfoEntity
import us.wedemy.eggeum.android.domain.model.place.MenuEntity
import us.wedemy.eggeum.android.domain.model.place.PlaceEntity
import us.wedemy.eggeum.android.domain.model.place.PlaceListEntity
import us.wedemy.eggeum.android.domain.model.place.ProductEntity
import us.wedemy.eggeum.android.domain.model.place.UpsertPlaceEntity

internal fun PlaceResponse.toEntity() =
  PlaceEntity(
    address1 = address1,
    address2 = address2,
    id = id,
    image = image?.toEntity(),
    info = info?.toEntity(),
    latitude = latitude,
    longitude = longitude,
    menu = menu?.toEntity(),
    name = name,
    type = type,
  )

internal fun PlaceEntity.toModel() =
  PlaceResponse(
    address1 = address1,
    address2 = address2,
    id = id,
    image = image?.toModel(),
    info = info?.toModel(),
    latitude = latitude,
    longitude = longitude,
    menu = menu?.toModel(),
    name = name,
    type = type,
  )

internal fun PlaceListResponse.toEntity() =
  PlaceListEntity(
    list = list.map(PlaceResponse::toEntity),
    totalPages = totalPages,
    totalElements = totalElements,
  )

internal fun UpsertPlaceEntity.toModel() =
  UpsertPlaceRequest(
    address1 = address1,
    address2 = address2,
    image = image?.toModel(),
    info = info.toModel(),
    latitude = latitude,
    longitude = longitude,
    menu = menu?.toModel(),
    name = name,
    placeId = placeId,
    remarks = remarks,
    type = type,
  )

public fun Info.toEntity(): InfoEntity =
  InfoEntity(
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

public fun InfoEntity.toModel(): Info =
  Info(
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

public fun Menu.toEntity(): MenuEntity =
  MenuEntity(
    products = products?.map { it.toEntity() },
  )

public fun MenuEntity.toModel(): Menu =
  Menu(
    products = products?.map { it.toModel() },
  )

public fun Product.toEntity(): ProductEntity =
  ProductEntity(
    name = name,
    price = price,
  )

public fun ProductEntity.toModel(): Product =
  Product(
    name = name,
    price = price,
  )
