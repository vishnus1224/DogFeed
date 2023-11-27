package com.dogbreed.feature.dog.listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dogbreed.common.unknownIfNullOrBlank
import com.dogbreed.data.model.Dog
import com.dogbreed.navigation.NavigationCommand

@Composable
fun DogListScreen(
  viewModel: DogListViewModel,
  navigator: (NavigationCommand) -> Unit,
  modifier: Modifier = Modifier,
) {
  val state = viewModel.state().collectAsStateWithLifecycle()

  LaunchedEffect(key1 = viewModel) {
    viewModel.navigation().collect(navigator)
  }

  DogListScreen(
    state = state.value,
    onDogClicked = viewModel::onDogClicked,
    modifier = modifier,
  )
}

@Composable
private fun DogListScreen(
  state: DogListState,
  onDogClicked: (Dog) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
  ) {
    // Top bar section
    TopAppBar(
      title = {
        Text(
          text = "Dog list",
        )
      },
      colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.background,
      )
    )

    // Content section
    if (!state.dogs.isNullOrEmpty()) {
      LazyColumn {
        itemsIndexed(state.dogs) { index, dog ->

          DogItem(
            dog = dog,
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onDogClicked(dog) }
              .padding(all = 8.dp)
          )

          // Add a divider if not the last item
          if (index != state.dogs.size - 1) {
            Divider()
          }
        }
      }
    } else if (state.inProgress) {
      // Wrap the content in a Box to display it in the center of the screen.
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
      ) {
        CircularProgressIndicator()
      }
    } else {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
      ) {
        Text(
          text = "Content not available. Please try again later.",
          modifier = Modifier.padding(all = 16.dp),
        )
      }
    }
  }
}

@Composable
private fun DogItem(
  dog: Dog,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
  ) {
    Text(text = "Name : ${dog.name.unknownIfNullOrBlank()}")

    Text(text = "Breed : ${dog.breedGroup.unknownIfNullOrBlank()}")

    Text(text = "Origin : ${dog.origin.unknownIfNullOrBlank()}")
  }
}

@Preview
@Composable
private fun DogListScreenPreview() {
  DogListScreen(
    state = DogListState(
      dogs = listOf(
        Dog(1, "abc", "abc", "abc", "abc")
      ),
      inProgress = false,
    ),
    onDogClicked = { },
  )
}