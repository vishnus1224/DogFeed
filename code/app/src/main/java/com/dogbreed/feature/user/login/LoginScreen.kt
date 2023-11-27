package com.dogbreed.feature.user.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dogbreed.navigation.NavigationCommand

@Composable
fun LoginScreen(
  viewModel: LoginViewModel,
  navigator: (NavigationCommand) -> Unit,
  modifier: Modifier = Modifier,
) {

  val state = viewModel.state().collectAsStateWithLifecycle()

  LaunchedEffect(key1 = viewModel) {
    viewModel.navigation().collect(navigator)
  }

  LoginScreen(
    state = state.value,
    onLoginClicked = viewModel::onLoginClicked,
    onRegisterClicked = viewModel::onRegisterClicked,
    onForgetPasswordClicked = viewModel::onForgetPasswordClicked,
    modifier = modifier,
  )
}

@Composable
private fun LoginScreen(
  state: LoginState,
  onLoginClicked: (String, String) -> Unit,
  onRegisterClicked: () -> Unit,
  onForgetPasswordClicked: () -> Unit,
  modifier: Modifier = Modifier,
) {

  // TODO Replace hardcoded values with strings from resources.

  Column(
    modifier = modifier,
  ) {

    // Top bar section
    TopAppBar(
      title = {
        Text(
          text = "Enter login credentials",
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
      modifier = Modifier.fillMaxHeight()
        .verticalScroll(rememberScrollState())
        .padding(all = 16.dp),
    ) {

      var username by remember { mutableStateOf("") }
      var password by remember { mutableStateOf("") }

      TextField(
        value = username,
        onValueChange = { username = it },
        label = {
          Text(text = "Username")
        },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
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
      )

      if (state.errorMessage != null) {
        Text(
          text = state.errorMessage,
          color = Color.Red,
          modifier = Modifier.padding(bottom = 8.dp),
        )
      }

      Button(
        onClick = { onLoginClicked(username, password) },
        enabled = state.inProgress.not(),
        modifier = Modifier.fillMaxWidth(),
      ) {
        if (state.inProgress) {
          CircularProgressIndicator()
        } else {
          Text(text = "Login")
        }
      }

      Text(
        text = "Forgot password ?",
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable { onForgetPasswordClicked() },
      )

      Spacer(modifier = Modifier.weight(1f))

      // Registration Section
      Text(
        text = "Don't have an account?",
        modifier = Modifier.padding(top = 16.dp),
      )

      Button(
        onClick = onRegisterClicked,
        modifier = Modifier.fillMaxWidth(),
      ) {
        Text(text = "Register")
      }
    }
  }
}

@Preview
@Composable
private fun LoginScreenPreview() {
  LoginScreen(
    state = LoginState(
      inProgress = false,
      errorMessage = "",
    ),
    onLoginClicked = { _, _ -> },
    onRegisterClicked = { },
    onForgetPasswordClicked = { },
  )
}