package com.dogbreed.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dog(
  @Json(name = "id") val id: Long,
  @Json(name = "name") val name: String,
  @Json(name = "breed_group") val breedGroup: String?,
  @Json(name = "origin") val origin: String?,
  @Json(name = "reference_image_id") val imageId: String,
)