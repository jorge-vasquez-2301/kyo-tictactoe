package com.example.tictactoe.controller

import com.example.tictactoe.domain.State
import com.example.tictactoe.mode.confirm.ConfirmMode
import com.example.tictactoe.mode.game.GameMode
import com.example.tictactoe.mode.menu.MenuMode
import kyo.*
import kyo.ios.*
import kyo.options.*

final case class ControllerLive(confirmMode: ConfirmMode, gameMode: GameMode, menuMode: MenuMode) extends Controller:
  def process(input: String, state: State): State > (Options & IOs) =
    state match
      case s: State.Confirm => Options.get(Some(confirmMode.process(input, s)))
      case s: State.Game    => gameMode.process(input, s).map(s => Options.get(Some(s)))
      case s: State.Menu    => Options.get(Some(menuMode.process(input, s)))
      case State.Shutdown   => Options.get(None)

  def render(state: State): String =
    state match
      case s: State.Confirm => confirmMode.render(s)
      case s: State.Game    => gameMode.render(s)
      case s: State.Menu    => menuMode.render(s)
      case State.Shutdown   => "Shutting down..."
