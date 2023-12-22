/*
 * Designed and developed by Wedemy 2023.
 *
 * Licensed under the MIT.
 * Please see full license: https://github.com/Wedemy/eggeum-android/blob/main/LICENSE
 */

package us.wedemy.eggeum.android.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import us.wedemy.eggeum.android.data.database.PlaceSearchDatabase

@Module
@InstallIn(SingletonComponent::class)
public object DatabaseModule {

  @Singleton
  @Provides
  public fun providePlaceSearchDatabase(@ApplicationContext context: Context): PlaceSearchDatabase =
    Room.databaseBuilder(
      context.applicationContext,
      PlaceSearchDatabase::class.java,
      "recent-search-places",
    ).build()
}
