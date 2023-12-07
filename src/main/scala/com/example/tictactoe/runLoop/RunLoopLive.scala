package com.example.tictactoe.runLoop

import com.example.tictactoe.controller.Controller
import com.example.tictactoe.domain.{ AppError, State }
import com.example.tictactoe.terminal.Terminal
import kyo.*
import kyo.ios.*
import kyo.options.*

final case class RunLoopLive(controller: Controller, terminal: Terminal) extends RunLoop:
  override def step(state: State): State > (Options & IOs) =
    for
      _         <- terminal.display(controller.render(state))
      input     <- if state == State.Shutdown then IOs.value("") else terminal.getUserInput
      nextState <- controller.process(input, state)
    yield nextState
