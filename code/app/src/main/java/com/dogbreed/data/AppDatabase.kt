package com.dogbreed.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dogbreed.data.dao.UserDao
import com.dogbreed.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun userDao(): UserDao
}