package com.dogbreed.common

fun String?.unknownIfNullOrBlank(): String =
  if (this.isNullOrBlank()) "Unknown"
  else this