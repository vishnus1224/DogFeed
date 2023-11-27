package com.dogbreed.feature.user.register

import androidx.lifecycle.viewModelScope
import com.dogbreed.base.BaseViewModel
import com.dogbreed.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

data class RegistrationState(
  val invalidUsername: Boolean,
  val invalidPassword: Boolean,
  val invalidName: Boolean,
  val inProgress: Boolean,
)

@HiltViewModel
class RegistrationViewModel
@Inject constructor(
  private val router: RegistrationRouter,
  private val userRepository: UserRepository,
) : BaseViewModel<RegistrationState>(router) {

  override fun initialState(): RegistrationState = RegistrationState(
    invalidUsername = false,
    invalidPassword = false,
    invalidName = false,
    inProgress = false,
  )

  fun onRegisterClicked(
    username: String,
    password: String,
    fullName: String,
  ) {
    updateState(
      currentState.copy(
        inProgress = true,
      )
    )

    val isUsernameValid = isUsernameValid(username)

    val isPasswordValid = isPasswordValid(password)

    val isNameValid = fullName.isNotBlank()

    if (isUsernameValid && isPasswordValid && isNameValid) {
      viewModelScope.launch {
        // Check if user already exists.
        val existingUser = userRepository.getUser(username)
        if (existingUser == null) {
          // Save the user details.
          userRepository.saveUser(username, password, fullName)
          router.goToDogListScreen()
        } else {
          // User already registered. Go to login.
          router.goToLoginScreen()
        }
      }
    } else {
      updateState(
        currentState.copy(
          inProgress = false,
          invalidUsername = isUsernameValid.not(),
          invalidPassword = isPasswordValid.not(),
          invalidName = isNameValid.not(),
        )
      )
    }

  }

  private fun isUsernameValid(username: String): Boolean {
    // User name is invalid if it is either blank or contains a white space character.
    return if (username.isBlank() or username.any { it.isWhitespace() }) {
      false
    } else {
      true
    }
  }

  private fun isPasswordValid(password: String): Boolean {
    return passwordRegex.matcher(password).matches()
  }

  private companion object {
    private val passwordRegex = Pattern.compile(
      "^" + // Match beginning of the string.
        "(?=.*[@#\\-+\$%.,?_!/\\\\{}()^&*~<=>'|`])" + // Match at least 1 special character.
        "(?=.*\\d)" + // Match at least 1 number.
        "(?=.*[A-Z])" + // Match at least 1 uppercase letter.
        "(?=\\S+$)" + // Match no whitespaces.
        ".{8,}" + // Match at least 8 characters.
        "$" // Match the end of the string.
    )
  }
}