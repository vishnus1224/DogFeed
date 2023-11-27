package com.dogbreed.navigation

interface NavigationCommand

data class GoToDestinationCommand(
  val route: String,
  val navigationOptions: NavigationOptions? = null,
) : NavigationCommand

data class NavigationOptions(
  val launchSingleTop: Boolean = false,
  val popUpToRoute: String? = null,
)

data object PopBackStackCommand : NavigationCommand

data class ShowToastCommand(
  val message: String,
) : NavigationCommand
