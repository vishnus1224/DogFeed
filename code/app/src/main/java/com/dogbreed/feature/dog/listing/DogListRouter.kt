package com.dogbreed.feature.dog.listing

import com.dogbreed.base.BaseRouter
import com.dogbreed.navigation.DOG_BREED_DETAILS_DESTINATION
import com.dogbreed.navigation.GoToDestinationCommand
import javax.inject.Inject

class DogListRouter
@Inject constructor(

) : BaseRouter() {

  fun goToDogBreedDetailsScreen(referenceImageId: String) {
    sendCommand(
      command = GoToDestinationCommand(
        route = "$DOG_BREED_DETAILS_DESTINATION/$referenceImageId"
      )
    )
  }
}