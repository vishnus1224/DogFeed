package com.dogbreed.base

import com.dogbreed.navigation.NavigationCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseRouter {

  private val commandsChannel = Channel<NavigationCommand>()

  private val coroutineScope = CoroutineScope(Job())

  fun navigationCommands(): Flow<NavigationCommand> = commandsChannel.receiveAsFlow()

  protected fun sendCommand(command: NavigationCommand) {
    coroutineScope.launch {
      commandsChannel.send(command)
    }
  }

  fun destroy() {
    coroutineScope.cancel()
  }
}