/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import us.wedemy.eggeum.android.data.model.place.Image
import us.wedemy.eggeum.android.data.model.place.Info
import us.wedemy.eggeum.android.data.model.place.Menu

internal class OrmConverter {
  private val json = Json

  @TypeConverter
  fun fromImage(image: Image?): String? {
    return image?.let { json.encodeToString(it) }
  }

  @TypeConverter
  fun toImage(imageString: String?): Image? {
    return imageString?.let { json.decodeFromString(it) }
  }

  @TypeConverter
  fun fromInfo(info: Info?): String? {
    return info?.let { json.encodeToString(it) }
  }

  @TypeConverter
  fun toInfo(infoString: String?): Info? {
    return infoString?.let { json.decodeFromString(it) }
  }

  @TypeConverter
  fun fromMenu(menu: Menu?): String? {
    return menu?.let { json.encodeToString(it) }
  }

  @TypeConverter
  fun toMenu(menuString: String?): Menu? {
    return menuString?.let { json.decodeFromString(it) }
  }
}
