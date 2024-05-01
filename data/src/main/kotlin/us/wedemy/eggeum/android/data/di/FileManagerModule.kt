package us.wedemy.eggeum.android.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import us.wedemy.eggeum.android.data.util.FileManager

@Module
@InstallIn(SingletonComponent::class)
internal object FileManagerModule {

  @Provides
  @Singleton
  fun provideFileManager(): FileManager {
    return FileManager
  }
}
