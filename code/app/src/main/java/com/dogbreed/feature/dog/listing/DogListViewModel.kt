package com.dogbreed.feature.dog.listing

import androidx.lifecycle.viewModelScope
import com.dogbreed.base.BaseViewModel
import com.dogbreed.data.model.Dog
import com.dogbreed.data.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DogListState(
  val dogs: List<Dog>?,
  val inProgress: Boolean,
)

@HiltViewModel
class DogListViewModel
@Inject constructor(
  private val router: DogListRouter,
  private val dogRepository: DogRepository,
) : BaseViewModel<DogListState>(router) {

  init {
    getDogList()
  }

  override fun initialState(): DogListState = DogListState(
    dogs = null,
    inProgress = false,
  )

  fun onDogClicked(dog: Dog) {
    router.goToDogBreedDetailsScreen(
      referenceImageId = dog.imageId,
    )
  }

  private fun getDogList() {
    updateState(
      currentState.copy(
        inProgress = true,
      )
    )

    viewModelScope.launch {
      val dogs = dogRepository.getDogList()

      updateState(
        currentState.copy(
          dogs = dogs,
          inProgress = false,
        )
      )
    }
  }
}