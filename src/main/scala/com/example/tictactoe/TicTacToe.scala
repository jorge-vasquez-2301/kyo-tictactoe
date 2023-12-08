package com.example.tictactoe

import com.example.tictactoe.controller.ControllerLive
import com.example.tictactoe.domain.State
import com.example.tictactoe.gameLogic.GameLogicLive
import com.example.tictactoe.mode.confirm.ConfirmModeLive
import com.example.tictactoe.mode.game.GameModeLive
import com.example.tictactoe.mode.menu.MenuModeLive
import com.example.tictactoe.opponentAi.OpponentAiLive
import com.example.tictactoe.parser.confirm.ConfirmCommandParserLive
import com.example.tictactoe.parser.game.GameCommandParserLive
import com.example.tictactoe.parser.menu.MenuCommandParserLive
import com.example.tictactoe.terminal.TerminalLive
import com.example.tictactoe.view.confirm.ConfirmViewLive
import com.example.tictactoe.view.game.GameViewLive
import com.example.tictactoe.view.menu.MenuViewLive
import kyo.*
import kyo.consoles.*
import kyo.envs.*
import kyo.ios.*
import kyo.options.*
import com.example.tictactoe.terminal.Terminal
import com.example.tictactoe.controller.Controller

object TicTacToe extends App:

  override def run(args: List[String]): Unit > App.Effects =
    Envs[Terminal]
      .run(TerminalLive()) {
        Envs[Controller].run[State, Envs[Terminal] & Consoles](controller)(program)
      }
      .unit

  val program: State > (Envs[Terminal] & Envs[Controller] & Consoles) =
    def loop(state: State): State > (Envs[Terminal] & Envs[Controller] & Consoles) =
      IOs {
        Options.run[State, Envs[Terminal] & Envs[Controller] & Consoles](step(state)).map {
          case Some(nextState) => loop(nextState)
          case None            => state
        }
      }

    loop(State.initial)

  def step(state: State): State > (Envs[Terminal] & Envs[Controller] & Options & Consoles) =
    for
      terminal   <- Envs[Terminal].get
      controller <- Envs[Controller].get
      _          <- terminal.display(controller.render(state))
      input      <- if state == State.Shutdown then "" else terminal.getUserInput
      nextState  <- controller.process(input, state)
    yield nextState

  val controller =
    val confirmCommandParser = ConfirmCommandParserLive()
    val confirmView          = ConfirmViewLive()
    val confirmMode          = ConfirmModeLive(confirmCommandParser, confirmView)

    val gameCommandParser = GameCommandParserLive()
    val gameView          = GameViewLive()
    val opponentAi        = OpponentAiLive()
    val gameLogic         = GameLogicLive()
    val gameMode          = GameModeLive(gameCommandParser, gameView, opponentAi, gameLogic)

    val menuCommandParser = MenuCommandParserLive()
    val menuView          = MenuViewLive()
    val menuMode          = MenuModeLive(menuCommandParser, menuView)

    ControllerLive(confirmMode, gameMode, menuMode)
