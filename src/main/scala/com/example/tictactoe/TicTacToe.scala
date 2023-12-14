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
import kyo.concurrent.fibers.*
import kyo.consoles.*
import kyo.direct.*
import kyo.envs.*
import kyo.ios.*
import kyo.options.*
import kyo.resources.*
import kyo.tries.*
import com.example.tictactoe.terminal.Terminal
import com.example.tictactoe.controller.Controller

object TicTacToe extends KyoApp:

  override def run: Any > (Fibers & Resources & Consoles & Tries) =
    Envs[Terminal]
      .run(TerminalLive()) {
        Envs[Controller].run[State, Envs[Terminal] & Consoles](controller)(program)
      }

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
    defer {
      val terminal   = await(Envs[Terminal].get)
      val controller = await(Envs[Controller].get)
      await(terminal.display(controller.render(state)))
      val input      = if state == State.Shutdown then "" else await(terminal.getUserInput)
      await(controller.process(input, state))
    }

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
