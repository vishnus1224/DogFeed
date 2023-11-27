package com.dogbreed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dogbreed.navigation.LOGIN_DESTINATION
import com.dogbreed.navigation.NavigationCommand
import com.dogbreed.navigation.dogBreedDetailsDestination
import com.dogbreed.navigation.dogBreedImageDestination
import com.dogbreed.navigation.dogListDestination
import com.dogbreed.navigation.extension.executeNavigationCommand
import com.dogbreed.navigation.loginDestination
import com.dogbreed.navigation.registrationDestination
import com.dogbreed.ui.theme.DogBreedTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {

      val navController = rememberNavController()

      val navigator: (NavigationCommand) -> Unit = remember {
        navController::executeNavigationCommand
      }

      DogBreedTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

          NavHost(
            navController = navController,
            startDestination = LOGIN_DESTINATION,
          ) {
            registrationDestination(navigator)
            loginDestination(navigator)
            dogListDestination(navigator)
            dogBreedDetailsDestination(navigator)
            dogBreedImageDestination()
          }
        }
      }
    }
  }
}
