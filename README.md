# DogFeed

## Approach(Key points)
Two users with same username cannot register in the app.    
Password validation is done using regular expressions.    
Password is hashed along with a salt value and then stored in the database.

## Architechture
The app is primarily built using the MVVM architechture.      
The Viewmodel is responsible to handle input events from the view and provide new view state by performing business logic.    
The Viewmodel exposes a StateFlow that emits a new state for the view to render whenever necessary.    
It also exposes a Flow of navigation events which the navigation component subscribes to and executes the navigation.    
The Repository pattern is used for getting data to the ViewModel.    
The Viewmodel also uses a Router to separate the navigation logic.    

The MVVM architechture used to build the app slightly varies from the traditional approach.
The variation is that the Viewmodel outputs only a single view state instead of multiple view states.
Since there is only one view state, it is easier to keep the UI in sync and prevent state overlaps.
This single view state is then fed to the Composable function to render the UI. 
When the view state changes, the Composable functions renders itself again with the new state(recomposition)
making it easier to reason about the Composition and Recomposition.

## Frameworks 
The app uses Jetpack Compose for UI.     
Coroutines are used for performing asynchronous tasks.      
User data is stored in the Sqlite database using the Room framework.     
Retrofit with Moshi is used for making network and parsing JSON.    
Coil is used for loading images from a remote url.    
Navigation component for Compose is used to handle in app navigation.    

## Package Structure
The app is structured into packages on a per feature basis.      
Code for login and registration can be found under .feature/user package.     
Code for dog list and details can be found under .feature/dog package.     
Data layer both database and network is separated into the .data package.     

## Improvements
Hardcoded strings have to replaced with localized one's.    
Error handling needs to be improved by checking the error code and showing user understandable message.    
Theming in jetpack compose has to be used for reusability.    
 
