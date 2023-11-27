package com.dogbreed.crypto

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

fun generateSalt(): ByteArray {
  val secureRandom = SecureRandom()
  val salt = ByteArray(16)
  secureRandom.nextBytes(salt)
  return salt
}

fun getPasswordHash(
  password: CharArray,
  salt: ByteArray
): ByteArray {
  val keySpec = PBEKeySpec(password, salt, 120000, 256)
  val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")

  val passwordHash: ByteArray = secretKeyFactory.generateSecret(keySpec).encoded

  return passwordHash
}