package com.dogbreed.feature.dog.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dogbreed.base.BaseViewModel
import com.dogbreed.data.model.BreedDetails
import com.dogbreed.data.repository.DogRepository
import com.dogbreed.navigation.ARG_REFERENCE_IMAGE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

data class DogBreedDetailsState(
  val breedDetails: BreedDetails?,
  val inProgress: Boolean,
  val errorMessage: String?,
)

@HiltViewModel
class DogBreedDetailsViewModel
@Inject constructor(
  private val router: DogBreedDetailsRouter,
  private val dogRepository: DogRepository,
  savedStateHandle: SavedStateHandle,
) : BaseViewModel<DogBreedDetailsState>(router) {

  private val referenceImageId: String = savedStateHandle[ARG_REFERENCE_IMAGE_ID]!!

  init {
    getBreedDetails()
  }

  override fun initialState(): DogBreedDetailsState = DogBreedDetailsState(
    breedDetails = null,
    inProgress = false,
    errorMessage = null,
  )

  fun onImageClicked(imageUrl: String) {
    val encodedUrl = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString())
    router.showFullscreenDogImage(encodedUrl)
  }

  private fun getBreedDetails() {
    updateState(
      currentState.copy(
        inProgress = true,
      )
    )

    viewModelScope.launch {
      try {
        val breedDetails = dogRepository.getDogBreedDetails(referenceImageId)

        updateState(
          currentState.copy(
            inProgress = false,
            breedDetails = breedDetails,
            errorMessage = null,
          )
        )
      } catch (e: Exception) {
        updateState(
          currentState.copy(
            inProgress = false,
            errorMessage = "Unable to get details at the moment."
          )
        )
      }
    }
  }
}