package com.example.tictactoe.mode.game

import com.example.tictactoe.domain.Board.Field
import com.example.tictactoe.domain.*
import com.example.tictactoe.gameLogic.GameLogic
import com.example.tictactoe.opponentAi.OpponentAi
import com.example.tictactoe.parser.game.GameCommandParser
import com.example.tictactoe.view.game.GameView
import kyo.*
import kyo.aborts.*
import kyo.ios.*

final case class GameModeLive(
  gameCommandParser: GameCommandParser,
  gameView: GameView,
  opponentAi: OpponentAi,
  gameLogic: GameLogic
) extends GameMode:
  def process(input: String, state: State.Game): State > IOs =
    if state.result != GameResult.Ongoing then State.Menu(None, MenuFooterMessage.Empty)
    else if isAiTurn(state) then
      opponentAi
        .randomMove(state.board)
        .flatMap(takeField(_, state))
    else
      Aborts[AppError].run {
        gameCommandParser
          .parse(input)
          .flatMap {
            case GameCommand.Menu       => State.Menu(Some(state), MenuFooterMessage.Empty)
            case GameCommand.Put(field) => takeField(field, state)
          }
      }.map(_.toOption.getOrElse(state.copy(footerMessage = GameFooterMessage.InvalidCommand)))

  private def isAiTurn(state: State.Game): Boolean =
    (state.turn == Piece.X && state.cross == Player.Ai) ||
      (state.turn == Piece.O && state.nought == Player.Ai)

  private def takeField(field: Field, state: State.Game): State > IOs =
    Aborts[AppError].run {
      for
        updatedBoard  <- gameLogic.putPiece(state.board, field, state.turn)
        updatedResult <- gameLogic.gameResult(updatedBoard)
        updatedTurn    = gameLogic.nextTurn(state.turn)
      yield state.copy(
        board = updatedBoard,
        result = updatedResult,
        turn = updatedTurn,
        footerMessage = GameFooterMessage.Empty
      )
    }.map(_.toOption.getOrElse(state.copy(footerMessage = GameFooterMessage.FieldOccupied)))

  def render(state: State.Game): String =
    val player = if state.turn == Piece.X then state.cross else state.nought
    List(
      gameView.header(state.result, state.turn, player),
      gameView.content(state.board, state.result),
      gameView.footer(state.footerMessage)
    ).mkString("\n\n")
