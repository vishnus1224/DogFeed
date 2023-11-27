package com.dogbreed.feature.user.login

import com.dogbreed.base.BaseRouter
import com.dogbreed.navigation.DOG_LIST_DESTINATION
import com.dogbreed.navigation.GoToDestinationCommand
import com.dogbreed.navigation.LOGIN_DESTINATION
import com.dogbreed.navigation.NavigationOptions
import com.dogbreed.navigation.REGISTRATION_DESTINATION
import com.dogbreed.navigation.ShowToastCommand
import javax.inject.Inject

class LoginRouter
@Inject constructor(

) : BaseRouter() {

  fun goToRegistrationScreen() {
    sendCommand(
      command = GoToDestinationCommand(
        route = REGISTRATION_DESTINATION,
      )
    )
  }

  fun goToDogListScreen() {
    sendCommand(
      command = GoToDestinationCommand(
        route = DOG_LIST_DESTINATION,
        navigationOptions = NavigationOptions(
          popUpToRoute = LOGIN_DESTINATION,
        )
      )
    )
  }

  fun goToForgetPasswordScreen() {
    sendCommand(
      command = ShowToastCommand(
        message = "To be implemented",
      )
    )
  }
}