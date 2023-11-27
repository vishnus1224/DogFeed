package com.dogbreed.base

import androidx.lifecycle.ViewModel
import com.dogbreed.navigation.NavigationCommand
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce

abstract class BaseViewModel<STATE>(
  private val router: BaseRouter,
) : ViewModel() {

  private val state by lazy {
    MutableStateFlow(initialState())
  }

  abstract fun initialState(): STATE

  fun state(): StateFlow<STATE> = state.asStateFlow()

  fun navigation(): Flow<NavigationCommand> = router.navigationCommands()
    .debounce(250) // Send only one command within a timeout to prevent processing single command multiple times.

  protected val currentState: STATE = state.value

  protected fun updateState(newState: STATE) {
    state.value = newState
  }

  override fun onCleared() {
    router.destroy()
    super.onCleared()
  }
}