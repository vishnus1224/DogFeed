package com.dogbreed.feature.dog.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dogbreed.R

@Composable
fun DogImageScreen(
  imageUrl: String,
  modifier: Modifier = Modifier,
) {
  Box(modifier = modifier) {
    AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .build(),
      contentDescription = "Dog image",
      modifier = Modifier.fillMaxSize(),
      error = painterResource(id = R.drawable.ic_launcher_background),
    )
  }
}