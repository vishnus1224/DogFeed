package com.dogbreed.navigation.extension

import android.widget.Toast
import androidx.navigation.NavController
import com.dogbreed.navigation.GoToDestinationCommand
import com.dogbreed.navigation.NavigationCommand
import com.dogbreed.navigation.PopBackStackCommand
import com.dogbreed.navigation.ShowToastCommand

fun NavController.executeNavigationCommand(command: NavigationCommand) {
  when (command) {
    is GoToDestinationCommand -> navigate(command.route) {
      command.navigationOptions?.let {
        launchSingleTop = it.launchSingleTop
        if (it.popUpToRoute != null) popUpTo(it.popUpToRoute) {
          inclusive = true
        }
      }
    }
    is PopBackStackCommand -> popBackStack()
    is ShowToastCommand -> {
      Toast.makeText(context, command.message, Toast.LENGTH_SHORT).show()
    }
    else -> error("Cannot perform navigation for $command")
  }
}