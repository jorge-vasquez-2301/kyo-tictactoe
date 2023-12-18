package com.example.tictactoe.controller

import com.example.tictactoe.domain.State
import com.example.tictactoe.mode.confirm.ConfirmMode
import com.example.tictactoe.mode.game.GameMode
import com.example.tictactoe.mode.menu.MenuMode
import kyo.*
import kyo.envs.*
import kyo.ios.*
import kyo.layers.*
import kyo.options.*

final class ControllerLive(confirmMode: ConfirmMode, gameMode: GameMode, menuMode: MenuMode) extends Controller:
  def process(input: String, state: State): State > (Options & IOs) =
    state match
      case s: State.Confirm => confirmMode.process(input, s)
      case s: State.Game    => gameMode.process(input, s)
      case s: State.Menu    => menuMode.process(input, s)
      case State.Shutdown   => Options.get(None)

  def render(state: State): String =
    state match
      case s: State.Confirm => confirmMode.render(s)
      case s: State.Game    => gameMode.render(s)
      case s: State.Menu    => menuMode.render(s)
      case State.Shutdown   => "Shutting down..."

object ControllerLive:
  val layer: Layer[Envs[Controller], Envs[ConfirmMode] & Envs[GameMode] & Envs[MenuMode]] =
    Envs[Controller].layer {
      for
        confirmMode <- Envs[ConfirmMode].get
        gameMode    <- Envs[GameMode].get
        menuMode    <- Envs[MenuMode].get
      yield ControllerLive(confirmMode, gameMode, menuMode)
    }
