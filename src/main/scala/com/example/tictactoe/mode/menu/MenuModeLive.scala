package com.example.tictactoe.mode.menu

import com.example.tictactoe.domain.*
import com.example.tictactoe.parser.menu.MenuCommandParser
import com.example.tictactoe.view.menu.MenuView
import kyo.*
import kyo.aborts.*
import kyo.envs.*
import kyo.layers.*

final class MenuModeLive(menuCommandParser: MenuCommandParser, menuView: MenuView) extends MenuMode:
  def process(input: String, state: State.Menu): State =
    Aborts[AppError].run {
      menuCommandParser
        .parse(input)
        .map {
          case MenuCommand.NewGame =>
            val newGameState =
              State.Game(
                Board.empty,
                Player.Human,
                Player.Ai,
                Piece.X,
                GameResult.Ongoing,
                GameFooterMessage.Empty
              )
            state.game match
              case Some(_) =>
                State.Confirm(ConfirmAction.NewGame, newGameState, state, ConfirmFooterMessage.Empty)
              case None    => newGameState
          case MenuCommand.Resume  =>
            state.game match
              case Some(gameState) => gameState
              case None            => state.copy(footerMessage = MenuFooterMessage.InvalidCommand)
          case MenuCommand.Quit    =>
            state.game match
              case Some(_) =>
                State.Confirm(ConfirmAction.Quit, State.Shutdown, state, ConfirmFooterMessage.Empty)
              case None    => State.Shutdown
        }
    }.pure.toOption.getOrElse(state.copy(footerMessage = MenuFooterMessage.InvalidCommand))

  def render(state: State.Menu): String =
    List(
      menuView.header,
      menuView.content(state.game.nonEmpty),
      menuView.footer(state.footerMessage)
    ).mkString("\n\n")

object MenuModeLive:
  val layer: Layer[Envs[MenuMode], Envs[MenuCommandParser] & Envs[MenuView]] =
    Envs[MenuMode].layer {
      for
        menuCommandParser <- Envs[MenuCommandParser].get
        menuView          <- Envs[MenuView].get
      yield MenuModeLive(menuCommandParser, menuView)
    }
