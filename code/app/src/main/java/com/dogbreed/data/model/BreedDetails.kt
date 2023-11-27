package com.dogbreed.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BreedDetails(
  @Json(name = "id") val referenceImageId: String,
  @Json(name = "url") val imageUrl: String,
  @Json(name = "breeds") val breeds: List<Breed>,
)

@JsonClass(generateAdapter = true)
data class Breed(
  @Json(name = "id") val dogId: Long,
  @Json(name = "name") val name: String,
  @Json(name = "bred_for") val bredFor: String?,
  @Json(name = "temperament") val temperament: String?,
  @Json(name = "origin") val origin: String?,
)