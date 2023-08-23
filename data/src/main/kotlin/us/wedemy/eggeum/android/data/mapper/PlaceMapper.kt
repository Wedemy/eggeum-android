/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.mapper

import us.wedemy.eggeum.android.data.model.File
import us.wedemy.eggeum.android.data.model.place.Image
import us.wedemy.eggeum.android.data.model.place.Info
import us.wedemy.eggeum.android.data.model.place.Menu
import us.wedemy.eggeum.android.data.model.place.PlaceBodyResponse
import us.wedemy.eggeum.android.data.model.place.PlaceListResponse
import us.wedemy.eggeum.android.data.model.place.Product
import us.wedemy.eggeum.android.domain.model.place.PlaceBody
import us.wedemy.eggeum.android.domain.model.place.PlaceList

internal fun PlaceBodyResponse.toDomain() =
  PlaceBody(
    address1 = address1,
    address2 = address2,
    id = id,
    image = image.toDomain(),
    info = info.toDomain(),
    latitude = latitude,
    longitude = longitude,
    menu = menu.toDomain(),
    name = name,
    type = type,
  )

internal fun PlaceListResponse.toDomain() =
  PlaceList(
    elements = list.map(PlaceBodyResponse::toDomain),
    totalPages = totalPages,
    totalElements = totalElements,
  )

public fun Image.toDomain(): us.wedemy.eggeum.android.domain.model.place.Image =
  us.wedemy.eggeum.android.domain.model.place.Image(
    files = files.map { it.toDomain() },
  )

public fun us.wedemy.eggeum.android.domain.model.place.Image.toData(): Image =
  Image(
    files = files.map { it.toData() },
  )

public fun File.toDomain(): us.wedemy.eggeum.android.domain.model.File =
  us.wedemy.eggeum.android.domain.model.File(
    uploadFileId = uploadFileId,
    url = url,
  )

public fun Info.toDomain(): us.wedemy.eggeum.android.domain.model.place.Info =
  us.wedemy.eggeum.android.domain.model.place.Info(
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

public fun us.wedemy.eggeum.android.domain.model.place.Info.toData(): Info =
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

public fun Menu.toDomain(): us.wedemy.eggeum.android.domain.model.place.Menu =
  us.wedemy.eggeum.android.domain.model.place.Menu(
    products = products.map { it.toDomain() },
  )

public fun us.wedemy.eggeum.android.domain.model.place.Menu.toData(): Menu =
  Menu(
    products = products.map { it.toData() },
  )

public fun Product.toDomain(): us.wedemy.eggeum.android.domain.model.place.Product =
  us.wedemy.eggeum.android.domain.model.place.Product(
    name = name,
    price = price,
  )

public fun us.wedemy.eggeum.android.domain.model.place.Product.toData(): Product =
  Product(
    name = name,
    price = price,
  )
