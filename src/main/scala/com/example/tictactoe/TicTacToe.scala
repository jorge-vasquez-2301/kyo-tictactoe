package com.example.tictactoe

import com.example.tictactoe.domain.State
import com.example.tictactoe.mode.confirm.ConfirmModeLive
import com.example.tictactoe.view.confirm.ConfirmViewLive
import kyo.*
import kyo.direct.*
import kyo.envs.*
import kyo.layers.*
import kyo.ios.*
import kyo.options.*
import com.example.tictactoe.terminal.Terminal
import com.example.tictactoe.controller.Controller
import com.example.tictactoe.parser.confirm.ConfirmCommandParserLive
import com.example.tictactoe.parser.menu.MenuCommandParserLive
import com.example.tictactoe.view.menu.MenuViewLive
import com.example.tictactoe.mode.menu.MenuModeLive
import com.example.tictactoe.parser.game.GameCommandParserLive
import com.example.tictactoe.view.game.GameViewLive
import com.example.tictactoe.mode.game.GameModeLive
import com.example.tictactoe.gameLogic.GameLogicLive
import com.example.tictactoe.opponentAi.OpponentAiLive
import com.example.tictactoe.controller.ControllerLive
import com.example.tictactoe.terminal.TerminalLive
import com.example.tictactoe.mode.confirm.ConfirmMode
import com.example.tictactoe.mode.menu.MenuMode
import com.example.tictactoe.mode.game.GameMode
import com.example.tictactoe.view.confirm.ConfirmView
import com.example.tictactoe.parser.confirm.ConfirmCommandParser
import com.example.tictactoe.parser.menu.MenuCommandParser
import com.example.tictactoe.view.menu.MenuView
import com.example.tictactoe.view.game.GameView
import com.example.tictactoe.opponentAi.OpponentAi
import com.example.tictactoe.gameLogic.GameLogic
import com.example.tictactoe.parser.game.GameCommandParser

object TicTacToe extends KyoApp:
  val program: State < (Envs[Terminal] & Envs[Controller] & IOs) =
    def step(state: State): Option[State] < (Envs[Terminal] & Envs[Controller] & IOs) =
      val nextState: State < (Envs[Terminal] & Envs[Controller] & IOs & Options) =
        defer {
          val terminal   = await(Envs[Terminal].get)
          val controller = await(Envs[Controller].get)
          await(terminal.display(controller.render(state)))
          val input      = if state == State.Shutdown then "" else await(terminal.getUserInput)
          await(controller.process(input, state))
        }

      Options.run(nextState)

    def loop(state: State): State < (Envs[Terminal] & Envs[Controller] & IOs) =
      IOs {
        step(state).map {
          case Some(nextState) => loop(nextState)
          case None            => state
        }
      }

    loop(State.initial)

  val layer: Layer[Envs[Controller] & Envs[Terminal], Any] =
    val confirmModeDeps: Layer[Envs[ConfirmCommandParser] & Envs[ConfirmView], Any] =
      ConfirmCommandParserLive.layer andThen ConfirmViewLive.layer

    val menuModeDeps: Layer[Envs[MenuCommandParser] & Envs[MenuView], Any] =
      MenuCommandParserLive.layer andThen MenuViewLive.layer

    val gameModeDeps: Layer[Envs[GameCommandParser] & Envs[GameView] & Envs[GameLogic] & Envs[OpponentAi], Any] =
      GameCommandParserLive.layer andThen GameViewLive.layer andThen GameLogicLive.layer andThen OpponentAiLive.layer

    val confirmModeNoDeps: Layer[Envs[ConfirmMode], Any] =
      ConfirmModeLive.layer chain confirmModeDeps

    val menuModeNoDeps: Layer[Envs[MenuMode], Any] =
      MenuModeLive.layer chain menuModeDeps

    val gameModeNoDeps: Layer[Envs[GameMode], Any] =
      GameModeLive.layer chain gameModeDeps

    val controllerNoDeps: Layer[Envs[Controller], Any] =
      ControllerLive.layer chain (confirmModeNoDeps andThen menuModeNoDeps andThen gameModeNoDeps)

    controllerNoDeps andThen TerminalLive.layer

  run {
    layer.run(program)
  }
