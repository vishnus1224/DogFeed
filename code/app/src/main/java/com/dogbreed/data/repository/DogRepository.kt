package com.dogbreed.data.repository

import com.dogbreed.data.model.BreedDetails
import com.dogbreed.data.model.Dog
import com.dogbreed.data.network.DogAPIService
import javax.inject.Inject

class DogRepository
@Inject constructor(
  private val apiService: DogAPIService,
) {
  // Retrofit uses IO dispatcher by default, so not specifying the dispatcher here.
  suspend fun getDogList(): List<Dog> {

    return try {
      apiService.getDogList(
        limit = 20,
        page = 0,
      )
    } catch (e: Exception) {
      // TODO Perform proper error handling and show user understandable error message.
      emptyList()
    }
  }

  suspend fun getDogBreedDetails(referenceImageId: String): BreedDetails {
    return apiService.getDogBreedDetails(referenceImageId)
  }
}