/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import us.wedemy.eggeum.android.data.model.place.PlaceResponse

@Database(
  entities = [PlaceResponse::class],
  version = 1,
  exportSchema = false,
)
@TypeConverters(OrmConverter::class)
public abstract class PlaceSearchDatabase : RoomDatabase() {
  public abstract fun placeSearchDao(): PlaceSearchDao
}
