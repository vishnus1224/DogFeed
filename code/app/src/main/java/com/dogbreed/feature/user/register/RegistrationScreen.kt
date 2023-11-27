package com.dogbreed.feature.user.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dogbreed.navigation.NavigationCommand

@Composable
fun RegistrationScreen(
  viewModel: RegistrationViewModel,
  navigator: (NavigationCommand) -> Unit,
  modifier: Modifier = Modifier,
) {
  val state = viewModel.state().collectAsStateWithLifecycle()
  
  LaunchedEffect(key1 = viewModel) {
    viewModel.navigation().collect(navigator)
  }
  
  RegistrationScreen(
    state = state.value,
    onRegisterClicked = viewModel::onRegisterClicked,
    modifier = modifier,
  )
}

@Composable
private fun RegistrationScreen(
  state: RegistrationState,
  onRegisterClicked: (String, String, String) -> Unit,
  modifier: Modifier = Modifier,
) {

  Column(
    modifier = modifier,
  ) {

    // Top bar section
    TopAppBar(
      title = {
        Text(
          text = "Registration form",
        )
      },
      colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.background,
      )
    )

    // Entering credentials section
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(all = 16.dp),
    ) {

      var name by remember { mutableStateOf("") }
      var username by remember { mutableStateOf("") }
      var password by remember { mutableStateOf("") }

      TextField(
        value = name,
        onValueChange = { name = it },
        label = {
          Text(text = "Full name")
        },
        supportingText = if (state.invalidName) {
          { Text(text = "Name cannot be blank") }
        } else null,
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 8.dp)
      )

      TextField(
        value = username,
        onValueChange = { username = it },
        label = {
          Text(text = "Username")
        },
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 8.dp),
        isError = state.invalidUsername,
        supportingText = if (state.invalidUsername) {
          { Text(text = "Username cannot contain spaces") }
        } else null
      )

      TextField(
        value = password,
        onValueChange = { password = it },
        label = {
          Text(text = "Password")
        },
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 8.dp),
        isError = state.invalidPassword,
        supportingText = if (state.invalidPassword) {
          { Text(text = "Password must be more than 8 characters and " +
            "contain atleast 1 uppercase letter, 1 number and 1 special symbol (@#-+$%.,?_!/\\{}()^&*~<=>'|`)") }
        } else null
      )

      Button(
        onClick = { onRegisterClicked(username, password, name) },
        enabled = state.inProgress.not(), // Enable button only if an operation is not in progress.
        modifier = Modifier.fillMaxWidth(),
      ) {
        if (state.inProgress) {
          CircularProgressIndicator()
        } else {
          Text(text = "Register")
        }
      }
    }
  }
}

@Preview
@Composable
private fun RegistrationScreenPreview() {
  RegistrationScreen(
    state = RegistrationState(
      invalidUsername = false,
      invalidPassword = false,
      invalidName = false,
      inProgress = false,
    ),
    onRegisterClicked = { _, _, _ -> },
  )
}