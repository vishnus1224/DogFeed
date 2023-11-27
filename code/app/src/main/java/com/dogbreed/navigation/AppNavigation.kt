package com.dogbreed.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dogbreed.feature.dog.detail.DogBreedDetailsScreen
import com.dogbreed.feature.dog.detail.DogImageScreen
import com.dogbreed.feature.dog.listing.DogListScreen
import com.dogbreed.feature.user.login.LoginScreen
import com.dogbreed.feature.user.register.RegistrationScreen

// Destinations
const val LOGIN_DESTINATION = "login"
const val REGISTRATION_DESTINATION = "register"
const val DOG_LIST_DESTINATION = "dogList"
const val DOG_BREED_DETAILS_DESTINATION = "dogBreedDetails"
const val DOG_BREED_IMAGE_DESTINATION = "dogBreedImage"

// Arguments
const val ARG_REFERENCE_IMAGE_ID = "imageId"
const val ARG_IMAGE_URL = "imageUrl"

// Navigation Graphs
fun NavGraphBuilder.loginDestination(
  navigator: (NavigationCommand) -> Unit,
) {
  composable(
    route = LOGIN_DESTINATION,
  ) {
    LoginScreen(
      viewModel = hiltViewModel(),
      navigator = navigator,
    )
  }
}

fun NavGraphBuilder.registrationDestination(
  navigator: (NavigationCommand) -> Unit,
) {
  composable(
    route = REGISTRATION_DESTINATION,
  ) {
    RegistrationScreen(
      viewModel = hiltViewModel(),
      navigator = navigator,
    )
  }
}

fun NavGraphBuilder.dogListDestination(
  navigator: (NavigationCommand) -> Unit,
) {
  composable(
    route = DOG_LIST_DESTINATION,
  ) {
    DogListScreen(
      viewModel = hiltViewModel(),
      navigator = navigator,
      modifier = Modifier.fillMaxSize(),
    )
  }
}

fun NavGraphBuilder.dogBreedDetailsDestination(
  navigator: (NavigationCommand) -> Unit,
) {
  composable(
    route = "$DOG_BREED_DETAILS_DESTINATION/{$ARG_REFERENCE_IMAGE_ID}",
    arguments = listOf(
      navArgument(ARG_REFERENCE_IMAGE_ID) { type = NavType.StringType },
    ),
  ) {
    DogBreedDetailsScreen(
      viewModel = hiltViewModel(),
      navigator = navigator,
    )
  }
}

fun NavGraphBuilder.dogBreedImageDestination() {
  composable(
    route = "$DOG_BREED_IMAGE_DESTINATION/{$ARG_IMAGE_URL}",
    arguments = listOf(
      navArgument(ARG_IMAGE_URL) { type = NavType.StringType },
    ),
  ) {
    val imageUrl = it.arguments?.getString(ARG_IMAGE_URL)!!
    DogImageScreen(
      imageUrl = imageUrl,
      modifier = Modifier.fillMaxSize(),
    )
  }
}