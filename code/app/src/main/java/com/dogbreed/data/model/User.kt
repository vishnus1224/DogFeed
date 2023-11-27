package com.dogbreed.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
  @PrimaryKey val username: String,
  @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val password: ByteArray,
  val fullName: String,
  @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val auth1: ByteArray,
)