/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import us.wedemy.eggeum.android.data.model.place.PlaceResponse

@Dao
public interface PlaceSearchDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun insertRecentSearchPlace(place: PlaceResponse)

  @Delete
  public suspend fun deleteRecentSearchPlace(place: PlaceResponse)

  @Query("SELECT * FROM places")
  public fun getRecentSearchPlaceList(): PagingSource<Int, PlaceResponse>
}
