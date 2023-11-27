package com.dogbreed.feature.user.login

import androidx.lifecycle.viewModelScope
import com.dogbreed.base.BaseViewModel
import com.dogbreed.crypto.getPasswordHash
import com.dogbreed.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class LoginState(
  val inProgress: Boolean,
  val errorMessage: String?,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val userRepository: UserRepository,
  private val loginRouter: LoginRouter,
) : BaseViewModel<LoginState>(loginRouter) {

  fun onLoginClicked(username: String, password: String) {

    if (username.isBlank() or password.isBlank()) {
      updateState(
        currentState.copy(
          inProgress = false,
          errorMessage = "Username or password cannot be blank."
        )
      )
      return
    }

    updateState(
      currentState.copy(
        inProgress = true,
      )
    )

    viewModelScope.launch {
      val existingUser = userRepository.getUser(username)

      // TODO Replace hardcoded values with strings from resources

      if (existingUser == null) {
        // User with specified user name does not exist. Show error.
        updateState(
          currentState.copy(
            inProgress = false,
            errorMessage = "User with given credentials does not exist."
          )
        )
      } else {
        // User exists. Check login credentials.
        val passwordHash = withContext(Dispatchers.Default) {
          getPasswordHash(
            password = password.toCharArray(),
            salt = existingUser.auth1,
          )
        }

        if (passwordHash.contentEquals(existingUser.password)) {
          // Password matches. Go to dog list screen.
          loginRouter.goToDogListScreen()
        } else {
          // Password does not match. Show error.
          updateState(
            currentState.copy(
              inProgress = false,
              errorMessage = "Invalid username or password."
            )
          )
        }
      }
    }
  }

  fun onRegisterClicked() {
    loginRouter.goToRegistrationScreen()
  }

  fun onForgetPasswordClicked() {
    loginRouter.goToForgetPasswordScreen()
  }

  override fun initialState(): LoginState = LoginState(
    inProgress = false,
    errorMessage = null,
  )
}