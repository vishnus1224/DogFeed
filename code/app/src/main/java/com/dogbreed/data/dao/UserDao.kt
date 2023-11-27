package com.dogbreed.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dogbreed.data.model.User

@Dao
interface UserDao {

  @Insert(onConflict = OnConflictStrategy.ABORT)
  suspend fun saveUser(user: User)

  @Query("SELECT * FROM user WHERE username = :username")
  suspend fun getUser(username: String): User?
}