package com.example.tictactoe.view.game

import com.example.tictactoe.domain.*

import kyo.envs.*
import kyo.layers.*

final class GameViewLive() extends GameView:
  def header(result: GameResult, turn: Piece, player: Player): String =
    result match
      case GameResult.Ongoing if player == Player.Human =>
        s"""$turn turn
           |
           |Select field number or type `menu` and confirm with <enter>.""".stripMargin
      case GameResult.Ongoing                           =>
        s"""$turn turn
           |
           |Calculating computer opponent move. Press <enter> to continue.""".stripMargin
      case GameResult.Win(piece)                        =>
        s"""The game ended with $piece win.
           |
           |Press <enter> to continue.""".stripMargin
      case GameResult.Draw                              =>
        s"""The game ended in a draw.
           |
           |Press <enter> to continue.""".stripMargin

  def content(board: Board, result: GameResult): String =
    Field.values
      .map(field => board.fields.get(field) -> field.ordinal)
      .map {
        case (Some(piece), _) => piece.toString
        case (None, value)    => value.toString
      }
      .sliding(3, 3)
      .map(fields => s""" ${fields.mkString(" ║ ")} """)
      .mkString("\n═══╬═══╬═══\n")

  def footer(message: GameFooterMessage): String =
    message match
      case GameFooterMessage.Empty          => ""
      case GameFooterMessage.InvalidCommand => "Invalid command. Try again."
      case GameFooterMessage.FieldOccupied  => "Field occupied. Try another."

object GameViewLive:
  val layer: Layer[Envs[GameView], Any] = Envs[GameView].layer(GameViewLive())
