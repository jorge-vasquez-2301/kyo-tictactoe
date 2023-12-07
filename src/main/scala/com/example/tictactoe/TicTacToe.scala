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
import com.example.tictactoe.runLoop.{RunLoop, RunLoopLive}
import com.example.tictactoe.terminal.TerminalLive
import com.example.tictactoe.view.confirm.ConfirmViewLive
import com.example.tictactoe.view.game.GameViewLive
import com.example.tictactoe.view.menu.MenuViewLive
import kyo.*
import kyo.consoles.*
import kyo.envs.*
import kyo.ios.*
import kyo.options.*

object TicTacToe extends App:

  override def run(args: List[String]): Unit > App.Effects =
    Envs[RunLoop].run(runLoop)(program).map(_ => ())

  val program: State > (Envs[RunLoop] & Consoles) =
    def loop(state: State): State > (Envs[RunLoop] & Consoles) =
      IOs {
        Options.run(Envs[RunLoop].get.map(_.step(state))).map {
          case Some(nextState) => loop(nextState)
          case None            => state
        }
      }

    loop(State.initial)

  val runLoop =
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

    val controller = ControllerLive(confirmMode, gameMode, menuMode)
    val terminal   = TerminalLive()

    RunLoopLive(controller, terminal)
