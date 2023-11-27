package com.dogbreed.feature.user.register

import com.dogbreed.base.BaseRouter
import com.dogbreed.navigation.DOG_LIST_DESTINATION
import com.dogbreed.navigation.GoToDestinationCommand
import com.dogbreed.navigation.LOGIN_DESTINATION
import com.dogbreed.navigation.NavigationOptions
import com.dogbreed.navigation.PopBackStackCommand
import javax.inject.Inject

class RegistrationRouter
@Inject constructor(

) : BaseRouter() {

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

  fun goToLoginScreen() {
    sendCommand(
      command = PopBackStackCommand,
    )
  }
}