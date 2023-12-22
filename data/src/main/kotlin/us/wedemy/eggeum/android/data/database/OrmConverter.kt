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

public class OrmConverter {
  private val json = Json

  @TypeConverter
  public fun fromImage(image: Image?): String? {
    return image?.let { json.encodeToString(it) }
  }

  @TypeConverter
  public fun toImage(imageString: String?): Image? {
    return imageString?.let { json.decodeFromString(it) }
  }

  @TypeConverter
  public fun fromInfo(info: Info?): String? {
    return info?.let { json.encodeToString(it) }
  }

  @TypeConverter
  public fun toInfo(infoString: String?): Info? {
    return infoString?.let { json.decodeFromString(it) }
  }

  @TypeConverter
  public fun fromMenu(menu: Menu?): String? {
    return menu?.let { json.encodeToString(it) }
  }

  @TypeConverter
  public fun toMenu(menuString: String?): Menu? {
    return menuString?.let { json.decodeFromString(it) }
  }
}
