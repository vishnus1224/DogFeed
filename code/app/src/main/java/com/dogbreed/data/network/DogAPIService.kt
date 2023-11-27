package com.dogbreed.data.network

import com.dogbreed.data.model.BreedDetails
import com.dogbreed.data.model.Dog
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DogAPIService {

  @GET("breeds")
  suspend fun getDogList(
    @Query("limit") limit: Int,
    @Query("page") page: Int,
  ): List<Dog>

  @GET("images/{reference_image_id}")
  suspend fun getDogBreedDetails(
    @Path("reference_image_id") referenceImageId: String
  ): BreedDetails
}