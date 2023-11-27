package com.dogbreed.feature.dog.detail

import com.dogbreed.base.BaseRouter
import com.dogbreed.navigation.DOG_BREED_IMAGE_DESTINATION
import com.dogbreed.navigation.GoToDestinationCommand
import javax.inject.Inject

class DogBreedDetailsRouter
@Inject constructor(

) : BaseRouter() {

  fun showFullscreenDogImage(imageUrl: String) {

    sendCommand(
      command = GoToDestinationCommand(
        route = "$DOG_BREED_IMAGE_DESTINATION/$imageUrl",
      )
    )
  }
}