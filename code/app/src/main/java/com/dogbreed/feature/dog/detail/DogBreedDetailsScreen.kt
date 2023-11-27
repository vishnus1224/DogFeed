package com.dogbreed.feature.dog.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dogbreed.R
import com.dogbreed.common.unknownIfNullOrBlank
import com.dogbreed.navigation.NavigationCommand

@Composable
fun DogBreedDetailsScreen(
  viewModel: DogBreedDetailsViewModel,
  navigator: (NavigationCommand) -> Unit,
  modifier: Modifier = Modifier,
) {
  val state = viewModel.state().collectAsStateWithLifecycle()

  LaunchedEffect(key1 = viewModel) {
    viewModel.navigation().collect(navigator)
  }

  DogBreedDetailsScreen(
    state = state.value,
    onImageClicked = viewModel::onImageClicked,
    modifier = modifier,
  )
}

@Composable
private fun DogBreedDetailsScreen(
  state: DogBreedDetailsState,
  onImageClicked: (String) -> Unit,
  modifier: Modifier = Modifier,
) {

  if (state.inProgress.not() && state.errorMessage == null) {
    Column(
      modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()),
    ) {
      val breedDetails = state.breedDetails

      // Top bar section
      TopAppBar(
        title = {
          Text(
            text = breedDetails?.breeds?.firstOrNull()?.name ?: "Dog Details",
          )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
          containerColor = MaterialTheme.colorScheme.primary,
          titleContentColor = MaterialTheme.colorScheme.background,
        )
      )

      AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
          .data(breedDetails?.imageUrl)
          .build(),
        contentDescription = "Dog image",
        modifier = Modifier
          .clickable { onImageClicked(breedDetails?.imageUrl!!) }
          .fillMaxWidth()
          .height(300.dp),
        contentScale = ContentScale.Crop,
        error = painterResource(id = R.drawable.ic_launcher_background),
      )

      val horizontalPadding16Dp = Modifier.padding(horizontal = 16.dp)

      with(breedDetails?.breeds?.firstOrNull()) {

        Text(
          text = "Bred For : ${this?.bredFor?.unknownIfNullOrBlank()}",
          modifier = horizontalPadding16Dp.padding(top = 16.dp),
        )

        Text(
          text = "Origin : ${this?.origin?.unknownIfNullOrBlank()}",
          modifier = horizontalPadding16Dp,
        )

        Text(
          text = "Temperament : ${this?.temperament?.unknownIfNullOrBlank()}",
          modifier = horizontalPadding16Dp.padding(bottom = 16.dp),
        )
      }
    }
  } else {
    Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center,
    ) {
      if (state.inProgress) {
        CircularProgressIndicator()
      } else {
        Text(
          text = state.errorMessage ?: "Something went wrong",
          modifier = Modifier.padding(all = 16.dp),
        )
      }
    }
  }
}

@Preview
@Composable
private fun DogBreedDetailsScreenPreview() {
  DogBreedDetailsScreen(
    state = DogBreedDetailsState(
      breedDetails = null,
      inProgress = false,
      errorMessage = "Something went wrong",
    ),
    onImageClicked = { },
  )
}