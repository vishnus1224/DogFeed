package com.dogbreed.data.repository

import com.dogbreed.crypto.generateSalt
import com.dogbreed.crypto.getPasswordHash
import com.dogbreed.data.dao.UserDao
import com.dogbreed.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository
@Inject constructor(
  private val userDao: UserDao,
) {

  suspend fun getUser(username: String): User? = withContext(Dispatchers.IO) {
    userDao.getUser(username)
  }

  suspend fun saveUser(
    username: String,
    password: String,
    fullName: String,
  ): Unit = withContext(Dispatchers.IO) {

    val salt = generateSalt()
    val passwordHash = getPasswordHash(
      password = password.toCharArray(),
      salt = salt,
    )

    val user = User(
      username = username,
      password = passwordHash,
      fullName = fullName,
      auth1 = salt,
    )

    userDao.saveUser(user)
  }
}