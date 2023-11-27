package com.dogbreed.di

import android.content.Context
import androidx.room.Room
import com.dogbreed.data.AppDatabase
import com.dogbreed.data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun provideDatabase(
    @ApplicationContext context: Context,
  ): AppDatabase {
    return Room.databaseBuilder(
      context = context,
      klass = AppDatabase::class.java,
      name = "dog-breed-database",
    ).build()
  }

  @Provides
  @Singleton
  fun provideUserDao(
    database: AppDatabase,
  ): UserDao = database.userDao()
}